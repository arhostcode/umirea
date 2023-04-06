package edu.mirea.ardyc.umirea.ui.view.auth.ui.authorization;

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

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.databinding.FragmentAuthorizationBinding;
import edu.mirea.ardyc.umirea.ui.view.AppActivity;
import edu.mirea.ardyc.umirea.ui.viewModel.auth.AuthorizationViewModel;


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
        authorizationViewModel.init();
        binding.enterButton.setOnClickListener(view -> {
            authorizationViewModel.loginToServer(binding.loginText.getText().toString(), binding.passwordText.getText().toString());
        });
        binding.resetPassword.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), AppActivity.class));
            getActivity().finish();
        });
        binding.createAccount.setOnClickListener(view -> {
            NavHostFragment.findNavController(this).navigate(R.id.navigation_registration);
        });
        authorizationViewModel.getErrorText().observe(getViewLifecycleOwner(), s -> Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_SHORT).show());
        authorizationViewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), s -> {
            startActivity(new Intent(getActivity(), AppActivity.class));
            getActivity().finish();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}