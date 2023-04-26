package edu.mirea.ardyc.umirea.ui.viewModel.group;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.data.repository.impl.group.RemoteGroupRepository;

public class GroupManagementViewModel extends AndroidViewModel {

    public GroupManagementViewModel(@NonNull Application application) {
        super(application);
    }

}
