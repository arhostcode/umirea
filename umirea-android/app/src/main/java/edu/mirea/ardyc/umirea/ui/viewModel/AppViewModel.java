package edu.mirea.ardyc.umirea.ui.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import edu.mirea.ardyc.umirea.data.model.chat.Chat;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;
import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;
import edu.mirea.ardyc.umirea.data.repository.impl.chat.ChatRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.chat.LocalChatRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.cloud.CloudLocalRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.cloud.CloudRepository;
import edu.mirea.ardyc.umirea.ui.viewModel.dashboard.DashboardService;
import edu.mirea.ardyc.umirea.ui.viewModel.group.GroupProcessor;

public class AppViewModel extends AndroidViewModel {


    private MutableLiveData<Timetable> timetableMutableLiveData;
    private MutableLiveData<Group> groupMutableLiveData;
    private MutableLiveData<Chat> chatMutableLiveData = new MutableLiveData<>(new Chat());
    private MutableLiveData<List<CloudFolder>> cloudFolderMutableLiveData = new MutableLiveData<>();


    //Repositories
    private ChatRepository chatRepository;

    private DashboardService dashboardService;
    private GroupProcessor groupProcessor;

    private CloudRepository cloudRepository;

    public AppViewModel(@NonNull Application application) {
        super(application);
        dashboardService = ((UmireaApplication) application).getDashboardProcessor();
        groupProcessor = ((UmireaApplication) application).getGroupProcessor();
        timetableMutableLiveData = dashboardService.initTimetableData();
        groupMutableLiveData = groupProcessor.initGroup();
        initChatData();
        initCloud();
    }

    private void initChatData() {
        chatRepository = new LocalChatRepository(getApplication());
        chatMutableLiveData.setValue(chatRepository.getData());
    }

    private void initCloud() {
        cloudRepository = new CloudLocalRepository(getApplication());
        cloudFolderMutableLiveData.setValue(cloudRepository.getData());
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
}
