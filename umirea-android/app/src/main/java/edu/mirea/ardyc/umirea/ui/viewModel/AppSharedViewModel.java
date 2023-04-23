package edu.mirea.ardyc.umirea.ui.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.mirea.ardyc.umirea.data.model.chat.Chat;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;
import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;

public class AppSharedViewModel extends ViewModel {

    private MutableLiveData<Timetable> timetableMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Group> groupMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Chat> chatMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<CloudFolder>> cloudFolderMutableLiveData = new MutableLiveData<>();


    public MutableLiveData<Timetable> getTimetableMutableLiveData() {
        return timetableMutableLiveData;
    }

    public MutableLiveData<Group> getGroupMutableLiveData() {
        return groupMutableLiveData;
    }

    public MutableLiveData<Chat> getChatMutableLiveData() {
        return chatMutableLiveData;
    }


    public MutableLiveData<List<CloudFolder>> getCloudFolderMutableLiveData() {
        return cloudFolderMutableLiveData;
    }
}
