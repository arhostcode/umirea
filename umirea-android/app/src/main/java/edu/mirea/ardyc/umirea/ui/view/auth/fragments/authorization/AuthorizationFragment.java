package edu.mirea.ardyc.umirea.ui.view.auth.fragments.authorization;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import dagger.hilt.android.AndroidEntryPoint;
import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.databinding.FragmentAuthorizationBinding;
import edu.mirea.ardyc.umirea.ui.view.AppActivity;
import edu.mirea.ardyc.umirea.ui.viewModel.auth.AuthorizationViewModel;

@AndroidEntryPoint
public class AuthorizationFragment extends Fragment {

    private FragmentAuthorizationBinding binding;
    private AuthorizationViewModel authorizationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        authorizationViewModel =
                new ViewModelProvider(this).get(AuthorizationViewModel.class);

        binding = FragmentAuthorizationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        authorizationViewModel = new ViewModelProvider(this).get(AuthorizationViewModel.class);

        initButtons();
        initObservers();

        return root;
    }

    private void initButtons() {
        requireActivity().getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).edit().putString("user_id", "ID2").apply();
        binding.enterButton.setOnClickListener(view -> {
            authorizationViewModel.loginToServer(binding.loginText.getText().toString(), binding.passwordText.getText().toString());
        });
//        binding.resetPassword.setOnClickListener(view -> {
//            NavHostFragment.findNavController(this).navigate(R.id.navigation_reset_password_mail);
//        });
        binding.createAccount.setOnClickListener(view -> {
            NavHostFragment.findNavController(this).navigate(R.id.navigation_registration);
        });
    }

    private void initObservers() {
        authorizationViewModel.getErrorText().observe(getViewLifecycleOwner(), s -> {
            if (s.isEmpty()) {
                return;
            }
            authorizationViewModel.clearErrorText();
            Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        });
        authorizationViewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), s -> {
            String group = requireActivity().getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_educationGroup", "null");
            if (group.equals("null") && (s == null || s.getEducationGroup().equals("null"))) {
                NavHostFragment.findNavController(this).navigate(R.id.navigation_chose_group);
                return;
            }

            startActivity(new Intent(getActivity(), AppActivity.class));
            requireActivity().finish();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}