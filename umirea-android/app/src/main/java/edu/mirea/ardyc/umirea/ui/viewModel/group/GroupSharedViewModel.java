package edu.mirea.ardyc.umirea.ui.viewModel.group;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.ui.viewModel.UmireaApplication;

public class GroupSharedViewModel extends AndroidViewModel {

    private MutableLiveData<Group> groupMutableLiveData;

    public GroupSharedViewModel(@NonNull Application application) {
        super(application);
        groupMutableLiveData = ((UmireaApplication) application).getGroupProcessor().getGroupMutableLiveData();
    }

    public MutableLiveData<Group> getGroupMutableLiveData() {
        return groupMutableLiveData;
    }
}
