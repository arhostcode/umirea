package edu.mirea.ardyc.umirea.ui.view.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import edu.mirea.ardyc.umirea.databinding.FragmentDashboardBinding;
import edu.mirea.ardyc.umirea.ui.view.dashboard.timetable.FullLessonItems;
import edu.mirea.ardyc.umirea.ui.view.dashboard.timetable.LessonItems;
import edu.mirea.ardyc.umirea.ui.viewModel.dashboard.DashboardViewModel;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dashboardViewModel.getTimetableMutableLiveData().observe(getViewLifecycleOwner(), (val) -> binding.calendar.reInit(val));
        binding.calendar.setOnClickCalendarDay((day) -> {
            if (day.getDay() != null)
                binding.lessons.setAdapter(new FullLessonItems(day.getDay().getLessons()));
            else
                binding.lessons.setAdapter(new FullLessonItems(new ArrayList<>()));
        });
        binding.lessons.setAdapter(new FullLessonItems(new ArrayList<>()));
        binding.lessons.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}