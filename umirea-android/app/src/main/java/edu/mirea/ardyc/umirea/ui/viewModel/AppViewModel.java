package edu.mirea.ardyc.umirea.ui.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.mirea.ardyc.umirea.data.model.auth.User;
import edu.mirea.ardyc.umirea.data.model.chat.Chat;
import edu.mirea.ardyc.umirea.data.model.chat.ChatMessage;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;
import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.data.model.info.InfoMessage;
import edu.mirea.ardyc.umirea.data.model.DataResponse;
import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;
import edu.mirea.ardyc.umirea.data.model.timetable.date.DateLesson;
import edu.mirea.ardyc.umirea.data.repository.impl.auth.UserRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.chat.ChatRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.cloud.CloudRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.group.GroupRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.info.InfoRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.DashboardRepository;
import edu.mirea.ardyc.umirea.ui.view.AppActivity;

@HiltViewModel
public class AppViewModel extends AndroidViewModel {

    private static final int NUMBER_OF_THREADS = 2;
    public static ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private ScheduledExecutorService scheduledExecutorService;

    private MutableLiveData<Timetable> timetableMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Group> groupMutableLiveData = new MutableLiveData<>(new Group());
    private MutableLiveData<Chat> chatMutableLiveData = new MutableLiveData<>(new Chat());
    private MutableLiveData<List<CloudFolder>> cloudFolderMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<InfoMessage>> infoMessages = new MutableLiveData<>();
    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>(new User("", "", "", "", "", ""));
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private boolean isHostConnected = true;

    //Repositories
    private ChatRepository chatRepository;
    private InfoRepository infoRepository;
    private UserRepository userRepository;
    private DashboardRepository dashboardRepository;
    private GroupRepository groupRepository;
    private CloudRepository cloudRepository;

    @Inject
    public AppViewModel(@NonNull Application application, InfoRepository infoRepository, ChatRepository chatRepository, DashboardRepository dashboardRepository, UserRepository userRepository, GroupRepository groupRepository, CloudRepository cloudRepository) {
        super(application);
        this.dashboardRepository = dashboardRepository;
        this.chatRepository = chatRepository;
        this.groupRepository = groupRepository;
        this.cloudRepository = cloudRepository;
        this.userRepository = userRepository;
        this.infoRepository = infoRepository;

        initLocalData();
        initUser();
    }

    private void initLocalData() {
        executorService.execute(() -> {
            groupMutableLiveData.postValue(groupRepository.getLocalGroup().getData());
            cloudFolderMutableLiveData.postValue(cloudRepository.loadLocalFolders().getData());
            chatMutableLiveData.postValue(new Chat(chatRepository.loadChatMessagesLocal().getData()));
            infoMessages.postValue(infoRepository.loadInfoLocal().getData());
            timetableMutableLiveData.postValue(dashboardRepository.getLocalDashboard().getData());
        });
    }

    public void initModules(User user) {
        if (scheduledExecutorService != null && !scheduledExecutorService.isTerminated())
            scheduledExecutorService.shutdown();
        scheduledExecutorService = Executors.newScheduledThreadPool(2);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                initGroup(user);
                initDashboard(user);
                initInfo(user);
                initCloud(user);

                if (!isHostConnected)
                    errorMessage.postValue("Соединение с сервером восстановлено");
                isHostConnected = true;
            } catch (Exception e) {
                e.printStackTrace();
                if (isHostConnected)
                    errorMessage.postValue("Соединение с сервером потеряно");
                isHostConnected = false;
            }
        }, 0, 10, TimeUnit.SECONDS);

        scheduledExecutorService.scheduleAtFixedRate(() -> initChat(user), 0, 1, TimeUnit.SECONDS);
    }

    private void initDashboard(User user) {
        DataResponse<Timetable> dashboard = dashboardRepository.loadDashboard(user.getToken());
        if (!dashboard.isError()) {
            timetableMutableLiveData.postValue(dashboard.getData());

        }
        DataResponse<List<DateLesson>> dataResponse = dashboardRepository.loadAddonLessons(user.getToken());
        if (!dataResponse.isError()) {
            dashboardRepository.loadHomeworks(user.getToken());
            dashboardRepository.loadNotes(user.getToken());
            timetableMutableLiveData.postValue(dashboardRepository.getLocalDashboard().getData());
        }
    }


    private void initUser() {
        executorService.execute(() -> {
            String token = getApplication().getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_token", null);
            if (token == null) {
                userMutableLiveData.setValue(null);
                return;
            }
            DataResponse<User> user = userRepository.getInfo(token);
            if (!user.isError()) {
                userMutableLiveData.postValue(user.getData());
            }
        });
    }


    private void initCloud(User user) throws Exception {
        DataResponse<List<CloudFolder>> cloudFolders = cloudRepository.getData(user.getToken());
        if (cloudFolders.getData() != null) {
            List<CloudFolder> cloudFolder = cloudFolders.getData();
            cloudFolderMutableLiveData.postValue(cloudFolder);
            return;
        }
        throw new Exception("Failed to load cloud folders");
    }

    private void initChat(User user) {
        Chat chat = chatMutableLiveData.getValue();
        if (chat == null)
            return;
        DataResponse<List<ChatMessage>> chatMessages = chatRepository.loadChatMessages(user.getToken());
        if (chatMessages.getData() != null) {
            chat.getChatMessages().addAll(chatMessages.getData());
            if (chatMessages.getData().size() > 0)
                chatMutableLiveData.postValue(chat);
        }
    }

    private void initInfo(User user) {
        List<InfoMessage> messages = infoMessages.getValue();
        if (messages == null)
            return;
        DataResponse<List<InfoMessage>> infoMessagesList = infoRepository.loadInfo(user.getToken());
        if (infoMessagesList.getData() != null) {
            messages.addAll(infoMessagesList.getData());
            if (infoMessagesList.getData().size() > 0)
                infoMessages.postValue(messages);
        }
    }

    private void initGroup(User user) throws Exception {
        DataResponse<Group> group = groupRepository.getRemoteGroup(user.getToken());
        if (!group.isError()) {
            groupMutableLiveData.postValue(group.getData());
            return;
        }
        throw new Exception("Failed to load group");
    }


    public MutableLiveData<Timetable> getTimetableMutableLiveData() {
        return timetableMutableLiveData;
    }

    public MutableLiveData<Group> getGroupMutableLiveData() {
        return groupMutableLiveData;
    }

    public MutableLiveData<Chat> getChatMutableLiveData() {
        return chatMutableLiveData;
    }

    public MutableLiveData<List<CloudFolder>> getCloudFolderMutableLiveData() {
        return cloudFolderMutableLiveData;
    }

    public MutableLiveData<List<InfoMessage>> getInfoMessages() {
        return infoMessages;
    }

    public LiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void shutdown() {
        if (scheduledExecutorService != null)
            scheduledExecutorService.shutdown();
    }
}
