package edu.mirea.ardyc.umirea.ui.viewModel.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.TimetableLocalRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.TimetableRemoteRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.TimetableRepository;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<Timetable> timetableMutableLiveData = new MutableLiveData<>();
    private TimetableRepository timetableRepository;

    public DashboardViewModel() {
        timetableRepository = new TimetableRemoteRepository();
        new Thread(() -> {
            timetableMutableLiveData.postValue(timetableRepository.getData());
        }).start();
    }

    public MutableLiveData<Timetable> getTimetableMutableLiveData() {
        return timetableMutableLiveData;
    }
}