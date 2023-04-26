package edu.mirea.ardyc.umirea.ui.view.auth.fragments.group;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.databinding.FragmentChoseGroupBinding;

public class ChoseGroupFragment extends Fragment {


    private FragmentChoseGroupBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChoseGroupBinding.inflate(getLayoutInflater());
        binding.createGroup.setOnClickListener((v) -> {
            NavHostFragment.findNavController(this).navigate(R.id.navigation_create_group);
        });
        return binding.getRoot();
    }
}