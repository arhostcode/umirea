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
import edu.mirea.ardyc.umirea.data.model.net.DataResponse;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.TimetableMemoryRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.TimetableRepository;
import edu.mirea.ardyc.umirea.ui.view.AppActivity;
import edu.mirea.ardyc.umirea.ui.viewModel.group.GroupService;

@HiltViewModel
public class CreateGroupViewModel extends AndroidViewModel {

    private MutableLiveData<List<String>> groupsList = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private GroupService groupService;
    private MutableLiveData<Group> groupMutableLiveData = new MutableLiveData<>();

    private TimetableRepository timetableRepository;

    @Inject
    public CreateGroupViewModel(@NotNull Application application, GroupService groupService) {
        super(application);
        timetableRepository = new TimetableMemoryRepository(application);
        groupsList.setValue(timetableRepository.listGroupsSchedules());
        this.groupService = groupService;
    }

    public MutableLiveData<List<String>> getGroupsList() {
        return groupsList;
    }

    public void createGroup(String groupName, String groupSchedule) {
        String token = getApplication().getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_token", "");
        new Thread(() -> {
            DataResponse<Group> groupDataResponse = groupService.createGroup(token,groupSchedule, groupName);
            if (groupDataResponse.getData() == null)
                error.postValue(groupDataResponse.getMessage());
            else {
                groupService.saveGroup(groupDataResponse.getData());
                getApplication().getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).edit()
                        .putString("user_educationGroup", groupDataResponse.getData().getUuid())
                        .apply();
            }
            groupMutableLiveData.postValue(groupDataResponse.getData());
        }).start();
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<Group> getGroupMutableLiveData() {
        return groupMutableLiveData;
    }
}
