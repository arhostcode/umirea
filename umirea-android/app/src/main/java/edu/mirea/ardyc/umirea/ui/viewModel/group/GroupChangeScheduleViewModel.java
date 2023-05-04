package edu.mirea.ardyc.umirea.ui.viewModel.group;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.ui.viewModel.UmireaApplication;
import edu.mirea.ardyc.umirea.ui.viewModel.dashboard.DashboardService;


@HiltViewModel
public class GroupChangeScheduleViewModel extends AndroidViewModel {

    private MutableLiveData<Group> groupMutableLiveData;
    private DashboardService dashboardService;

    @Inject
    public GroupChangeScheduleViewModel(Application application, DashboardService dashboardService) {
        super(application);
        this.dashboardService = dashboardService;
    }

    public LiveData<List<String>> getGroups() {
        MutableLiveData<List<String>> groupSchedulesList = new MutableLiveData<>();
        new Thread(() -> {
            groupSchedulesList.postValue(dashboardService.getGroupsSchedules());
        }).start();
        return groupSchedulesList;
    }

    public void setGroupMutableLiveData(MutableLiveData<Group> groupMutableLiveData) {
        this.groupMutableLiveData = groupMutableLiveData;
    }
}
