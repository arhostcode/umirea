package edu.mirea.ardyc.umirea.ui.view.auth.fragments.resetPassword;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.databinding.FragmentResetPasswordMailBinding;

public class ResetPasswordMail extends Fragment {

    private FragmentResetPasswordMailBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentResetPasswordMailBinding.inflate(getLayoutInflater());
        initButtons();
        return binding.getRoot();
    }

    private void initButtons() {
        binding.enterButton.setOnClickListener((view) -> {
            NavHostFragment.findNavController(this).navigate(R.id.navigation_reset_password_data);
        });

    }

}