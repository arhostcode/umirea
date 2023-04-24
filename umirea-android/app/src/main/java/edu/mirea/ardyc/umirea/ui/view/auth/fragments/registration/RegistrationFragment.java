package edu.mirea.ardyc.umirea.ui.view.auth.fragments.registration;

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
import edu.mirea.ardyc.umirea.databinding.FragmentRegistrationBinding;
import edu.mirea.ardyc.umirea.ui.viewModel.auth.RegistrationViewModel;


public class RegistrationFragment extends Fragment {

    private FragmentRegistrationBinding binding;
    private RegistrationViewModel registerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        registerViewModel =
                new ViewModelProvider(this).get(RegistrationViewModel.class);

        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        registerViewModel.init();
        initButtons();
        initObservers();

        return root;
    }

    private void initObservers() {
        registerViewModel.getErrorText().observe(getViewLifecycleOwner(), s -> Toast.makeText(requireActivity().getApplicationContext(), s, Toast.LENGTH_SHORT).show());

    }

    private void initButtons() {
        binding.verify.setOnClickListener(view -> registerViewModel.verify(binding.loginText.getText().toString(), binding.passwordText.getText().toString(), binding.firstName.getText().toString(), binding.lastName.getText().toString()));
        binding.enterButton.setOnClickListener(view -> {
//            if (binding.code.getText().toString().isEmpty()) {
//                Toast.makeText(getActivity().getApplicationContext(), "Получите код подтверждения на почту. Проверьте папку спам.", Toast.LENGTH_SHORT).show();
//            } else {
//
//            }
            NavHostFragment.findNavController(this).navigate(R.id.navigation_chose_group);
//                registerViewModel.register(binding.loginText.getText().toString(), binding.passwordText.getText().toString(), binding.firstName.getText().toString(), binding.lastName.getText().toString(), binding.code.getText().toString());
        });
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}