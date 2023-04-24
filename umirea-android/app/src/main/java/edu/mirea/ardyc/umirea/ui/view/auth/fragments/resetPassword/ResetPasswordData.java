package edu.mirea.ardyc.umirea.ui.view.auth.fragments.resetPassword;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.mirea.ardyc.umirea.databinding.FragmentResetPasswordDataBinding;
import edu.mirea.ardyc.umirea.ui.view.AppActivity;

public class ResetPasswordData extends Fragment {

    private FragmentResetPasswordDataBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentResetPasswordDataBinding.inflate(getLayoutInflater());
        initButtons();
        return binding.getRoot();
    }

    private void initButtons(){
        binding.enterButton.setOnClickListener((view) -> {
            startActivity(new Intent(getActivity(), AppActivity.class));
            getActivity().finish();
        });
    }
}