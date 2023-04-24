package edu.mirea.ardyc.umirea.ui.view.auth.fragments.group;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.databinding.FragmentCreateGroupBinding;
import edu.mirea.ardyc.umirea.ui.viewModel.auth.CreateGroupViewModel;

public class CreateGroupFragment extends Fragment {

    private FragmentCreateGroupBinding binding;
    private CreateGroupViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateGroupBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(requireActivity()).get(CreateGroupViewModel.class);
        initObservers();
        return binding.getRoot();
    }

    private void initObservers() {
        viewModel.getGroupsList().observe(getViewLifecycleOwner(), (val) -> {
            if (val != null)
                binding.schedulesList.setAdapter(new ArrayAdapter<>(getContext(), R.layout.group_item, val));
        });
    }
}