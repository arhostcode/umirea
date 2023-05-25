package edu.mirea.ardyc.umirea.ui.viewModel.auth;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.data.model.DataResponse;
import edu.mirea.ardyc.umirea.data.repository.impl.group.GroupRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.DashboardRepository;
import edu.mirea.ardyc.umirea.ui.view.AppActivity;
import edu.mirea.ardyc.umirea.ui.viewModel.AppViewModel;

@HiltViewModel
public class CreateGroupViewModel extends AndroidViewModel {

    private MutableLiveData<List<String>> groupsList = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<Group> groupMutableLiveData = new MutableLiveData<>();
    private GroupRepository groupRepository;
    private DashboardRepository dashboardRepository;


    @Inject
    public CreateGroupViewModel(@NotNull Application application, GroupRepository groupRepository, DashboardRepository dashboardRepository) {
        super(application);
        this.dashboardRepository = dashboardRepository;
        AppViewModel.executorService.execute(() -> {
            DataResponse<List<String>> dataResponse = dashboardRepository.listGroupsSchedules();
            if (!dataResponse.isError())
                groupsList.postValue(dataResponse.getData());
            else error.postValue(dataResponse.getMessage());
        });
        this.groupRepository = groupRepository;
    }

    public MutableLiveData<List<String>> getGroupsList() {
        return groupsList;
    }

    public void createGroup(String groupName, String groupSchedule) {
        String token = getApplication().getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_token", "");
        AppViewModel.executorService.execute(() -> {
            DataResponse<Group> groupDataResponse = groupRepository.createGroup(token, groupSchedule, groupName);
            if (groupDataResponse.getData() == null)
                error.postValue(groupDataResponse.getMessage());
            else {
                groupRepository.saveGroup(groupDataResponse.getData());
                getApplication().getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).edit()
                        .putString("user_educationGroup", groupDataResponse.getData().getUuid())
                        .apply();
            }
            groupMutableLiveData.postValue(groupDataResponse.getData());
        });
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<Group> getGroupMutableLiveData() {
        return groupMutableLiveData;
    }
}
