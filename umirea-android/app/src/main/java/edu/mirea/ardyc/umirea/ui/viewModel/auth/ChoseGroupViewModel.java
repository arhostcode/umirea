package edu.mirea.ardyc.umirea.ui.viewModel.auth;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.data.model.net.DataResponse;
import edu.mirea.ardyc.umirea.ui.view.AppActivity;
import edu.mirea.ardyc.umirea.ui.viewModel.group.GroupService;

@HiltViewModel
public class ChoseGroupViewModel extends AndroidViewModel {

    private final GroupService groupService;

    private MutableLiveData<Group> groupMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    @Inject
    public ChoseGroupViewModel(@NonNull Application application, GroupService groupService) {
        super(application);
        this.groupService = groupService;
    }

    public void chooseGroup(String groupName, String groupToken) {
        new Thread(() -> {
            DataResponse<Group> groupDataResponse = groupService.connectToGroup(getApplication().getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_token", ""), groupName, groupToken);
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

    public MutableLiveData<Group> getGroupMutableLiveData() {
        return groupMutableLiveData;
    }

    public MutableLiveData<String> getError() {
        return error;
    }
}
