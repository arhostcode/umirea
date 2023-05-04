package edu.mirea.ardyc.umirea.ui.viewModel.group;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.data.model.group.Member;
import edu.mirea.ardyc.umirea.data.model.net.DataResponse;
import edu.mirea.ardyc.umirea.ui.view.AppActivity;

@HiltViewModel
public class GroupSharedViewModel extends AndroidViewModel {

    private final GroupService groupService;
    private MutableLiveData<Group> groupMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessageMutableLiveData = new MutableLiveData<>();

    @Inject
    public GroupSharedViewModel(@NonNull Application application, GroupService groupService) {
        super(application);
        this.groupService = groupService;
        updateGroup();
    }

    private void updateGroupSync() {
        String userToken = getApplication().getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_token", "");
        DataResponse<Group> groupDataResponse = groupService.loadRemoteGroup(userToken);
        if (groupDataResponse.getData() == null) {
            return;
        }
        groupMutableLiveData.postValue(groupDataResponse.getData());
    }

    private void updateGroup() {
        new Thread(this::updateGroupSync).start();
    }

    public MutableLiveData<Group> getGroupMutableLiveData() {
        return groupMutableLiveData;
    }

    public MutableLiveData<String> getErrorMessageMutableLiveData() {
        return errorMessageMutableLiveData;
    }

    public void changeSchedule(String schedule) {
        new Thread(() -> {
            String userToken = getApplication().getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_token", "");
            try {
                DataResponse<Group> groupDataResponse = groupService.changeSchedule(schedule, userToken, groupMutableLiveData.getValue());
                if (groupDataResponse.isError()) {
                    errorMessageMutableLiveData.postValue("Ошибка при изменении расписания");
                    return;
                }

                groupMutableLiveData.postValue(groupDataResponse.getData());
            } catch (Exception e) {
                errorMessageMutableLiveData.postValue("Ошибка при изменении расписания");
            }
        }).start();
    }

    public void kickMember(Member member) {
        new Thread(() -> {
            String userToken = getApplication().getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_token", "");
            try {
                groupService.kickMember(userToken, member.getUuid());
            } catch (Exception e) {
                e.printStackTrace();
                errorMessageMutableLiveData.postValue("Ошибка при изменении расписания");
            }
            updateGroupSync();
        }).start();
    }
}
