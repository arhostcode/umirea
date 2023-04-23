package edu.mirea.ardyc.umirea.ui.view.cloud;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;
import edu.mirea.ardyc.umirea.databinding.FragmentCloudBinding;
import edu.mirea.ardyc.umirea.ui.view.cloud.adapters.CloudFolderAdapter;
import edu.mirea.ardyc.umirea.ui.viewModel.AppSharedViewModel;
import edu.mirea.ardyc.umirea.ui.viewModel.cloud.CloudViewModel;

public class CloudFragment extends Fragment {

    private FragmentCloudBinding binding;
    private CloudViewModel cloudViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cloudViewModel =
                new ViewModelProvider(this).get(CloudViewModel.class);

        binding = FragmentCloudBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        AppSharedViewModel appSharedViewModel = new ViewModelProvider(requireActivity()).get(AppSharedViewModel.class);
        binding.folders.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.folders.setAdapter(new CloudFolderAdapter(new ArrayList<>()));
        appSharedViewModel.getCloudFolderMutableLiveData().observe(getViewLifecycleOwner(), (data) -> {
            ((CloudFolderAdapter) binding.folders.getAdapter()).update(data);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}