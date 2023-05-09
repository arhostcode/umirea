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
import edu.mirea.ardyc.umirea.data.model.DataResponse;
import edu.mirea.ardyc.umirea.data.repository.impl.group.GroupRepository;
import edu.mirea.ardyc.umirea.ui.view.AppActivity;
import edu.mirea.ardyc.umirea.ui.viewModel.AppViewModel;

@HiltViewModel
public class GroupSharedViewModel extends AndroidViewModel {

    private final GroupRepository groupRepository;
    private MutableLiveData<Group> groupMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessageMutableLiveData = new MutableLiveData<>();

    @Inject
    public GroupSharedViewModel(@NonNull Application application, GroupRepository groupRepository) {
        super(application);
        this.groupRepository = groupRepository;
        updateGroup();
    }

    private void updateGroupSync() {
        String userToken = getApplication().getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_token", "");
        DataResponse<Group> groupDataResponse = groupRepository.getRemoteGroup(userToken);
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
        AppViewModel.executorService.execute(() -> {
            String userToken = getApplication().getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_token", "");
            try {
                groupRepository.changeSchedule(userToken, schedule);
                DataResponse<Group> groupDataResponse = groupRepository.getRemoteGroup(userToken);
                if (groupDataResponse.isError()) {
                    errorMessageMutableLiveData.postValue("Ошибка при изменении расписания");
                    return;
                }

                groupMutableLiveData.postValue(groupDataResponse.getData());
            } catch (Exception e) {
                errorMessageMutableLiveData.postValue("Ошибка при изменении расписания");
            }
        });
    }

    public void kickMember(Member member) {
        new Thread(() -> {
            String userToken = getApplication().getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_token", "");
            try {
                groupRepository.kickMember(userToken, member.getUuid());
            } catch (Exception e) {
                e.printStackTrace();
                errorMessageMutableLiveData.postValue("Ошибка при изменении расписания");
            }
            updateGroupSync();
        }).start();
    }
}
