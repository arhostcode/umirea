package edu.mirea.ardyc.umirea.ui.view.group.fragments.memberSettings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.mirea.ardyc.umirea.databinding.FragmentGroupMemberSettingsBinding;
import edu.mirea.ardyc.umirea.ui.view.group.fragments.memberSettings.adapters.MemberListAdapter;
import edu.mirea.ardyc.umirea.ui.viewModel.group.GroupMemberSettingsViewModel;
import edu.mirea.ardyc.umirea.ui.viewModel.group.GroupSharedViewModel;

public class GroupMemberSettings extends Fragment {

    private FragmentGroupMemberSettingsBinding binding;
    private GroupMemberSettingsViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGroupMemberSettingsBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(requireActivity()).get(GroupMemberSettingsViewModel.class);
        GroupSharedViewModel groupSharedViewModel = new ViewModelProvider(requireActivity()).get(GroupSharedViewModel.class);
        MemberListAdapter adapter = new MemberListAdapter(null);
        adapter.setMemberConsumer(groupSharedViewModel::kickMember);
        binding.members.setAdapter(adapter);
        binding.members.setLayoutManager(new LinearLayoutManager(requireContext()));
        groupSharedViewModel.getGroupMutableLiveData().observe(getViewLifecycleOwner(), (val) -> {
            ((MemberListAdapter) binding.members.getAdapter()).update(val);
        });
        return binding.getRoot();
    }
}