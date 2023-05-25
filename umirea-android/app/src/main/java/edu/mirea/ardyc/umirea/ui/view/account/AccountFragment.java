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

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.databinding.FragmentAccountBinding;
import edu.mirea.ardyc.umirea.ui.view.AppActivity;
import edu.mirea.ardyc.umirea.ui.view.auth.AuthorizationActivity;
import edu.mirea.ardyc.umirea.ui.view.group.GroupManagementActivity;
import edu.mirea.ardyc.umirea.ui.viewModel.AppSharedViewModel;
import edu.mirea.ardyc.umirea.ui.viewModel.AppViewModel;
import edu.mirea.ardyc.umirea.ui.viewModel.account.AccountViewModel;
import edu.mirea.ardyc.umirea.ui.viewModel.chat.ChatViewModel;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private AppSharedViewModel appSharedViewModel;
    private AppViewModel appViewModel;
    private AccountViewModel accountViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel =
                new ViewModelProvider(requireActivity()).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        appSharedViewModel = new ViewModelProvider(requireActivity()).get(AppSharedViewModel.class);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        initObservers();
        initListeners();
        return root;
    }

    private void initObservers(){
        String id = requireActivity().getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_uuid", "");
        appSharedViewModel.getGroupMutableLiveData().observe(getViewLifecycleOwner(), (val) -> {
            if (val == null || val.getMembers().isEmpty())
                return;
            binding.myGroup.setText(String.format(getResources().getString(R.string.user_group), val.getName()));
            binding.myRole.setText(String.format(getResources().getString(R.string.user_role), getRussianRole(val.getById(id).getRole())));
            if (!val.getById(id).getRole().equals("owner"))
                binding.groupSettings.setVisibility(View.GONE);
        });

        appSharedViewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), (val) -> {
            binding.myName.setText(String.format(getResources().getString(R.string.user_full_name), val.getFirstName(), val.getLastName()));
            binding.myMail.setText(String.format(getResources().getString(R.string.user_login), val.getLogin()));
        });
    }

    private void initListeners(){
        binding.groupSettings.setOnClickListener((view) -> {
            startActivity(new Intent(requireContext(), GroupManagementActivity.class));
            requireActivity().overridePendingTransition(0, 0);
        });
        binding.exitAccount.setOnClickListener((view) -> {
            appViewModel.shutdown();
            startActivity(new Intent(requireActivity(), AuthorizationActivity.class));
            requireActivity().finish();
            accountViewModel.removeUserData();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private String getRussianRole(String role) {
        switch (role) {
            case "owner":
                return getResources().getString(R.string.user_role_owner);
            case "member":
                return getResources().getString(R.string.user_role_member);
            default:
                return getResources().getString(R.string.user_role_member);
        }
    }
}