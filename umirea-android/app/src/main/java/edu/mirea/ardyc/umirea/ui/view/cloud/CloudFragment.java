package edu.mirea.ardyc.umirea.ui.view.cloud;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.mirea.ardyc.umirea.databinding.FragmentCloudBinding;

public class CloudFragment extends Fragment {

    private FragmentCloudBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CloudViewModel cloudViewModel =
                new ViewModelProvider(this).get(CloudViewModel.class);

        binding = FragmentCloudBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}