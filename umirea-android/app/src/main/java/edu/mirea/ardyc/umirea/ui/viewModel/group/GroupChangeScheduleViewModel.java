package edu.mirea.ardyc.umirea.ui.viewModel.group;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.data.repository.impl.group.RemoteGroupRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.TimetableLocalRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.TimetableMemoryRepository;
import edu.mirea.ardyc.umirea.ui.viewModel.UmireaApplication;
import edu.mirea.ardyc.umirea.ui.viewModel.dashboard.DashboardProcessor;

public class GroupChangeScheduleViewModel extends AndroidViewModel {

    public GroupChangeScheduleViewModel(Application application) {
        super(application);
    }

    public LiveData<List<String>> getGroups() {
        MutableLiveData<List<String>> groupSchedulesList = new MutableLiveData<>();
        new Thread(() -> {
            groupSchedulesList.postValue(((UmireaApplication) getApplication()).getDashboardProcessor().getGroupsSchedules());
        }).start();
        return groupSchedulesList;
    }

    public void updateSchedule(String schedule) {
        ((UmireaApplication) getApplication()).getGroupProcessor().updateBaseSchedule(schedule);
    }
}
