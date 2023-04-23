package edu.mirea.ardyc.umirea.ui.view.group.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.databinding.FragmentGroupSettingsBinding;
import edu.mirea.ardyc.umirea.ui.viewModel.group.GroupSharedViewModel;

public class GroupSettings extends Fragment {
    private FragmentGroupSettingsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGroupSettingsBinding.inflate(getLayoutInflater());
        GroupSharedViewModel groupSharedViewModel = new ViewModelProvider(requireActivity()).get(GroupSharedViewModel.class);
        groupSharedViewModel.getGroupMutableLiveData().observe(getViewLifecycleOwner(), (val) -> {
            binding.groupToken.setText(val.getToken());
            binding.groupSchedule.setText(val.getBaseSchedule());
            binding.groupName.setText(val.getName());
            binding.groupMemberCount.setText(String.valueOf(val.getMembers().size()));
        });
        binding.memberSettings.setOnClickListener((view) -> NavHostFragment.findNavController(this).navigate(R.id.navigation_group_member_settings));
        binding.scheduleSettings.setOnClickListener((view) -> NavHostFragment.findNavController(this).navigate(R.id.navigation_group_schedule_settings));
        return binding.getRoot();
    }
}