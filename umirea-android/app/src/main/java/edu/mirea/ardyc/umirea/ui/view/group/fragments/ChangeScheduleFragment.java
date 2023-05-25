package edu.mirea.ardyc.umirea.ui.view.group.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import dagger.hilt.android.AndroidEntryPoint;
import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.databinding.FragmentChangeScheduleBinding;
import edu.mirea.ardyc.umirea.ui.viewModel.group.GroupChangeScheduleViewModel;
import edu.mirea.ardyc.umirea.ui.viewModel.group.GroupSharedViewModel;
import es.dmoral.toasty.Toasty;

@AndroidEntryPoint
public class ChangeScheduleFragment extends Fragment {
    private FragmentChangeScheduleBinding binding;
    private GroupChangeScheduleViewModel viewModel;
    private GroupSharedViewModel groupSharedViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangeScheduleBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(requireActivity()).get(GroupChangeScheduleViewModel.class);
        groupSharedViewModel = new ViewModelProvider(requireActivity()).get(GroupSharedViewModel.class);
        viewModel.setGroupMutableLiveData(groupSharedViewModel.getGroupMutableLiveData());
        initObservers();
        initListeners();
        return binding.getRoot();
    }

    private void initObservers() {
        viewModel.getGroups().observe(getViewLifecycleOwner(), (val) -> {
            if (val != null)
                binding.schedulesList.setAdapter(new ArrayAdapter<>(getContext(), R.layout.group_item, val));
        });
    }

    private void initListeners() {
        binding.apply.setOnClickListener(v -> {
            String text = binding.schedulesList.getText().toString();
            if (!viewModel.getGroups().getValue().contains(text)) {
                Toasty.info(requireContext(), "Расписание не найдено").show();
                return;
            }
            groupSharedViewModel.changeSchedule(binding.schedulesList.getText().toString());
            NavHostFragment.findNavController(this).popBackStack();
        });
    }


}