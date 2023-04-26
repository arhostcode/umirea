package edu.mirea.ardyc.umirea.ui.viewModel.group;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.ui.viewModel.UmireaApplication;

public class GroupChangeScheduleViewModel extends AndroidViewModel {

    private MutableLiveData<Group> groupMutableLiveData;
    public GroupChangeScheduleViewModel(Application application) {
        super(application);
    }

    public LiveData<List<String>> getGroups() {
        MutableLiveData<List<String>> groupSchedulesList = new MutableLiveData<>();
        new Thread(() -> {
            groupSchedulesList.postValue(((UmireaApplication) getApplication()).getDashboardProcessor().getGroupsSchedules());
        }).start();
        return groupSchedulesList;
    }

    public void updateSchedule(String schedule) {
        Group group = groupMutableLiveData.getValue();
        group.setBaseSchedule(schedule);
        groupMutableLiveData.postValue(group);
        ((UmireaApplication) getApplication()).getGroupProcessor().updateBaseSchedule(schedule);
    }

    public void setGroupMutableLiveData(MutableLiveData<Group> groupMutableLiveData) {
        this.groupMutableLiveData = groupMutableLiveData;
    }
}