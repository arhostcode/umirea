package edu.mirea.ardyc.umirea.ui.viewModel.group;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.DashboardRepository;
import edu.mirea.ardyc.umirea.ui.viewModel.AppViewModel;


@HiltViewModel
public class GroupChangeScheduleViewModel extends AndroidViewModel {

    private MutableLiveData<Group> groupMutableLiveData;
    MutableLiveData<List<String>> groupSchedulesList = new MutableLiveData<>();

    private DashboardRepository dashboardRepository;

    @Inject
    public GroupChangeScheduleViewModel(Application application, DashboardRepository dashboardService) {
        super(application);
        this.dashboardRepository = dashboardService;
        initGroupsList();
    }

    private void initGroupsList() {
        AppViewModel.executorService.execute(() -> {
            groupSchedulesList.postValue(dashboardRepository.listGroupsSchedules().getData());
        });
    }

    public LiveData<List<String>> getGroups() {
        return groupSchedulesList;
    }

    public void setGroupMutableLiveData(MutableLiveData<Group> groupMutableLiveData) {
        this.groupMutableLiveData = groupMutableLiveData;
    }
}
