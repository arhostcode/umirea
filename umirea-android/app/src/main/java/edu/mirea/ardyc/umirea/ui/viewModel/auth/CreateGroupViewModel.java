package edu.mirea.ardyc.umirea.ui.viewModel.auth;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import edu.mirea.ardyc.umirea.data.repository.impl.timetable.TimetableLocalRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.TimetableMemoryRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.TimetableRemoteRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.TimetableRepository;

public class CreateGroupViewModel extends AndroidViewModel {

    private MutableLiveData<List<String>> groupsList = new MutableLiveData<>();
    private TimetableRepository timetableRepository;

    public CreateGroupViewModel(@NotNull Application application) {
        super(application);
        timetableRepository = new TimetableMemoryRepository(application);
        groupsList.setValue(timetableRepository.listGroupsSchedules());

    }

    public MutableLiveData<List<String>> getGroupsList() {
        return groupsList;
    }
}
