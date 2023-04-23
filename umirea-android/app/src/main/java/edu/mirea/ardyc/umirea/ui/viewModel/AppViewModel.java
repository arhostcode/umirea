package edu.mirea.ardyc.umirea.ui.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import edu.mirea.ardyc.umirea.data.model.chat.Chat;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;
import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;
import edu.mirea.ardyc.umirea.data.model.timetable.data.DateLesson;
import edu.mirea.ardyc.umirea.data.repository.impl.chat.ChatRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.chat.LocalChatRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.cloud.CloudLocalRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.cloud.CloudRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.TimetableLocalRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.TimetableMemoryRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.TimetableRepository;
import edu.mirea.ardyc.umirea.ui.viewModel.dashboard.DashboardProcessor;
import edu.mirea.ardyc.umirea.ui.viewModel.group.GroupProcessor;

public class AppViewModel extends AndroidViewModel {

    private MutableLiveData<Chat> chatMutableLiveData = new MutableLiveData<>(new Chat());
    private MutableLiveData<List<CloudFolder>> cloudFolderMutableLiveData = new MutableLiveData<>();


    //Repositories
    private ChatRepository chatRepository;

    private DashboardProcessor dashboardProcessor;
    private GroupProcessor groupProcessor;

    private CloudRepository cloudRepository;

    public AppViewModel(@NonNull Application application) {
        super(application);
        dashboardProcessor = ((UmireaApplication) application).getDashboardProcessor();
        groupProcessor = ((UmireaApplication) application).getGroupProcessor();
        dashboardProcessor.initTimetableData();
        groupProcessor.initGroup();
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
        return dashboardProcessor.getTimetableMutableLiveData();
    }

    public MutableLiveData<Group> getGroupMutableLiveData() {
        return groupProcessor.getGroupMutableLiveData();
    }

    public MutableLiveData<Chat> getChatMutableLiveData() {
        return chatMutableLiveData;
    }

    public MutableLiveData<List<CloudFolder>> getCloudFolderMutableLiveData() {
        return cloudFolderMutableLiveData;
    }
}
