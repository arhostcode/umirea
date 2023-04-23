package edu.mirea.ardyc.umirea.ui.view.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.mirea.ardyc.umirea.databinding.FragmentAccountBinding;
import edu.mirea.ardyc.umirea.ui.view.auth.AuthorizationActivity;
import edu.mirea.ardyc.umirea.ui.view.group.GroupManagementActivity;
import edu.mirea.ardyc.umirea.ui.viewModel.AppSharedViewModel;
import edu.mirea.ardyc.umirea.ui.viewModel.chat.ChatViewModel;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ChatViewModel accountViewModel =
                new ViewModelProvider(this).get(ChatViewModel.class);

        String id = requireActivity().getSharedPreferences("edu.mirea.ardyc.umirea", Context.MODE_PRIVATE).getString("user_id", "");

        AppSharedViewModel appSharedViewModel = new ViewModelProvider(requireActivity()).get(AppSharedViewModel.class);
        appSharedViewModel.getGroupMutableLiveData().observe(getViewLifecycleOwner(), (val) -> {
            binding.myName.setText(val.getById(id).getFirstName() + " " + val.getById(id).getLastName());
            binding.myGroup.setText("Группа - " + val.getName());
            binding.myRole.setText("Роль - " + val.getById(id).getRole());
        });

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.groupSettings.setOnClickListener((view) -> startActivity(new Intent(requireContext(), GroupManagementActivity.class)));
        binding.exitAccount.setOnClickListener((view) -> {
            startActivity(new Intent(requireActivity(), AuthorizationActivity.class));
            requireActivity().finish();
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}