package edu.mirea.ardyc.umirea.ui.view.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.data.model.timetable.date.DateTask;
import edu.mirea.ardyc.umirea.databinding.FragmentDashboardBinding;
import edu.mirea.ardyc.umirea.ui.view.dashboard.dialogs.LessonAdderDialog;
import edu.mirea.ardyc.umirea.ui.view.dashboard.adapters.FullLessonItems;
import edu.mirea.ardyc.umirea.ui.view.dashboard.adapters.LessonItems;
import edu.mirea.ardyc.umirea.ui.viewModel.AppSharedViewModel;
import edu.mirea.ardyc.umirea.ui.viewModel.dashboard.DashboardViewModel;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private DashboardViewModel dashboardViewModel;
    private MutableLiveData<Boolean> postInit = new MutableLiveData<>(false);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashboardViewModel =
                new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.calendar.init(getContext());
        AppSharedViewModel appSharedViewModel = new ViewModelProvider(requireActivity()).get(AppSharedViewModel.class);
        appSharedViewModel.getTimetableMutableLiveData().observe(getViewLifecycleOwner(), (val) -> {
            binding.calendar.reInit(val);
            System.out.println("Reinited");
        });
        dashboardViewModel.setTimetableMutableLiveData(appSharedViewModel.getTimetableMutableLiveData());

        binding.lessons.setAdapter(new FullLessonItems(new ArrayList<>(), this::updateHomework, this::updateTask));
        binding.calendar.setOnClickCalendarDay((day) -> {
            if (day.getDay() != null)
                ((LessonItems) binding.lessons.getAdapter()).update(day.getDay());
            else
                ((LessonItems) binding.lessons.getAdapter()).update(new ArrayList<>());
        });

        binding.lessons.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.addLesson.setOnClickListener((view) -> {
            new LessonAdderDialog(getContext(), dashboardViewModel::addLesson).show();
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateHomework(DateTask dateTask) {
        dashboardViewModel.updateHomework(dateTask);
    }

    private void updateTask(DateTask dateTask) {
        dashboardViewModel.updateTask(dateTask);
    }
}