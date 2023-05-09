package edu.mirea.ardyc.umirea.ui.view.auth.fragments.group;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.databinding.FragmentChoseGroupBinding;
import edu.mirea.ardyc.umirea.ui.view.AppActivity;
import edu.mirea.ardyc.umirea.ui.viewModel.auth.ChoseGroupViewModel;

public class ChoseGroupFragment extends Fragment {


    private FragmentChoseGroupBinding binding;
    private ChoseGroupViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChoseGroupBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(requireActivity()).get(ChoseGroupViewModel.class);
        binding.createGroup.setOnClickListener((v) -> {
            NavHostFragment.findNavController(this).navigate(R.id.navigation_create_group);
        });
        binding.enterButton.setOnClickListener((v) -> {
            viewModel.chooseGroup(binding.groupName.getText().toString(), binding.groupToken.getText().toString());
        });
        initObservers();
        return binding.getRoot();
    }


    private void initObservers() {
        viewModel.getError().observe(getViewLifecycleOwner(), (val) -> {
            if (val == null) {
                return;
            }
            Toast.makeText(getContext(), val, Toast.LENGTH_SHORT).show();
            viewModel.getError().setValue(null);
        });

        viewModel.getGroupMutableLiveData().observe(getViewLifecycleOwner(), (group) -> {
            if (group == null) {
                return;
            }
            startActivity(new Intent(requireActivity(), AppActivity.class));
            requireActivity().finish();
        });

    }
}