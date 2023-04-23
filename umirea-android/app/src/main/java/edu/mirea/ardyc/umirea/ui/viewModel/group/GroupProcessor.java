package edu.mirea.ardyc.umirea.ui.viewModel.group;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.data.repository.impl.group.GroupRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.group.RemoteGroupRepository;

public class GroupProcessor {

    private static GroupProcessor instance;

    private MutableLiveData<Group> groupMutableLiveData = new MutableLiveData<>();

    private GroupRepository remoteGroupRepository;
    private Group group;
    private Application application;

    public static GroupProcessor getInstance(Application application) {
        if (instance == null)
            instance = new GroupProcessor(application);
        return instance;
    }

    private GroupProcessor(Application application) {
        this.application = application;
    }

    public Group getGroup() {
        return group;
    }

    public MutableLiveData<Group> getGroupMutableLiveData() {
        return groupMutableLiveData;
    }

    public void initGroup() {
        remoteGroupRepository = new RemoteGroupRepository(application);
        group = remoteGroupRepository.getData();
        groupMutableLiveData.postValue(group);
    }

    public void updateBaseSchedule(String baseSchedule) {
        group.setBaseSchedule(baseSchedule);
        groupMutableLiveData.postValue(group);
    }
}
