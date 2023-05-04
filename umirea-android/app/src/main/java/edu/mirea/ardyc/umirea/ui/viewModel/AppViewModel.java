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
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;
import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.data.model.info.InfoMessage;
import edu.mirea.ardyc.umirea.data.model.net.DataResponse;
import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;
import edu.mirea.ardyc.umirea.data.repository.impl.chat.ChatRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.chat.LocalChatRepository;
import edu.mirea.ardyc.umirea.ui.view.AppActivity;
import edu.mirea.ardyc.umirea.ui.viewModel.auth.UserService;
import edu.mirea.ardyc.umirea.ui.viewModel.cloud.CloudService;
import edu.mirea.ardyc.umirea.ui.viewModel.dashboard.DashboardService;
import edu.mirea.ardyc.umirea.ui.viewModel.group.GroupService;
import edu.mirea.ardyc.umirea.ui.viewModel.info.InfoService;

@HiltViewModel
public class AppViewModel extends AndroidViewModel {

    private static final int NUMBER_OF_THREADS = 2;
    public static ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

    private MutableLiveData<Timetable> timetableMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Group> groupMutableLiveData = new MutableLiveData<>(new Group());
    private MutableLiveData<Chat> chatMutableLiveData = new MutableLiveData<>(new Chat());
    private MutableLiveData<List<CloudFolder>> cloudFolderMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<InfoMessage>> infoMessages;
    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>(new User("", "", "", "", "", ""));

    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private boolean isHostConnected = true;

    //Repositories
    private ChatRepository chatRepository;
    private InfoService infoService;
    private DashboardService dashboardService;
    private GroupService groupService;

    private CloudService cloudService;

    @Inject
    public AppViewModel(@NonNull Application application, InfoService infoService, DashboardService dashboardService, UserService userService, GroupService groupService, CloudService cloudService) {
        super(application);
        this.dashboardService = dashboardService;
        this.infoService = infoService;
        this.groupService = groupService;
        this.cloudService = cloudService;
        dashboardService.initTimetableData(timetableMutableLiveData);
        infoMessages = infoService.getMutableInfoData();
        initLocalData();
        initUser(userService);
    }

    private void initLocalData() {
        executorService.execute(() -> {
            groupMutableLiveData.postValue(groupService.loadLocalGroup().getData());
            cloudFolderMutableLiveData.postValue(cloudService.loadLocalFolders().getData());
        });
    }

    public void initModules(User user) {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                initGroup(user);
                initChatData(user);
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
    }


    private void initUser(UserService userService) {
        executorService.execute(() -> {
            String token = getApplication().getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_token", null);
            if (token == null) {
                userMutableLiveData.setValue(null);
                return;
            }
            DataResponse<User> user = userService.getInfo(token);
            if (!user.isError()) {
                userMutableLiveData.postValue(user.getData());
            }
        });
    }

    private void initChatData(User user) {
        chatRepository = new LocalChatRepository(getApplication());
//        chatMutableLiveData.setValue(chatRepository.getData());
    }

    private void initCloud(User user) throws Exception {
        DataResponse<List<CloudFolder>> cloudFolders = cloudService.getFolders(user.getToken());
        if (cloudFolders.getData() != null) {
            List<CloudFolder> cloudFolder = cloudFolders.getData();
            cloudFolderMutableLiveData.postValue(cloudFolder);
            return;
        }
        throw new Exception("Failed to load cloud folders");
    }

    private void initGroup(User user) throws Exception {
        DataResponse<Group> group = groupService.loadRemoteGroup(user.getToken());
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
}
