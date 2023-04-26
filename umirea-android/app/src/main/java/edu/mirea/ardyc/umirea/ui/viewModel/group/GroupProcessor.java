package edu.mirea.ardyc.umirea.ui.viewModel.group;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.data.model.group.Member;
import edu.mirea.ardyc.umirea.data.repository.impl.group.GroupRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.group.LocalGroupRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.group.RemoteGroupRepository;

public class GroupProcessor {


    //private MutableLiveData<Group> groupMutableLiveData = new MutableLiveData<>();
    private GroupRepository remoteGroupRepository;
    private LocalGroupRepository localGroupRepository;
    private Application application;

    public static GroupProcessor create(Application application) {
        return new GroupProcessor(application);
    }

    private GroupProcessor(Application application) {
        this.application = application;
    }


    public MutableLiveData<Group> initGroup() {
        remoteGroupRepository = new RemoteGroupRepository(application);
        localGroupRepository = new LocalGroupRepository(application);
        return loadGroup();
    }

    public MutableLiveData<Group> loadGroup() {
        MutableLiveData<Group> groupMutableLiveData = new MutableLiveData<>();
        if (application.getSharedPreferences("edu.mirea.ardyc.umirea", Context.MODE_PRIVATE).contains("group_name")) {
            localGroupRepository.getDataAndPerform((group) -> processGroup(group, groupMutableLiveData));
        } else {
            Group group = remoteGroupRepository.getData();
            localGroupRepository.updateGroup(group);
            processGroup(group, groupMutableLiveData);
        }
        return groupMutableLiveData;
    }

    private void processGroup(Group group, MutableLiveData<Group> groupMutableLiveData) {
        groupMutableLiveData.postValue(group);
    }

    public void updateBaseSchedule(String baseSchedule) {
        // TODO: 24.04.2023
        localGroupRepository.updateGroupSchedule(baseSchedule);
    }

    public void updateMembers(List<Member> members) {
        // TODO: 24.04.2023
        localGroupRepository.updateMembers(members);
    }
}
