package edu.mirea.ardyc.umirea.ui.view.cloud;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;
import edu.mirea.ardyc.umirea.BuildConfig;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFile;
import edu.mirea.ardyc.umirea.databinding.FragmentCloudBinding;
import edu.mirea.ardyc.umirea.ui.view.cloud.adapters.CloudFolderAdapter;
import edu.mirea.ardyc.umirea.ui.viewModel.AppSharedViewModel;
import edu.mirea.ardyc.umirea.ui.viewModel.cloud.CloudViewModel;
import es.dmoral.toasty.Toasty;

@AndroidEntryPoint
public class CloudFragment extends Fragment {

    private FragmentCloudBinding binding;
    private CloudViewModel cloudViewModel;
    private AppSharedViewModel appSharedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cloudViewModel =
                new ViewModelProvider(this).get(CloudViewModel.class);

        binding = FragmentCloudBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        appSharedViewModel = new ViewModelProvider(requireActivity()).get(AppSharedViewModel.class);
        cloudViewModel.setCloudMutableLiveData(appSharedViewModel.getCloudFolderMutableLiveData());
        cloudViewModel.getMessageMutableLiveData().observe(getViewLifecycleOwner(), (data) -> {
            if (data == null || data.isEmpty())
                return;
            Toasty.info(requireContext(), data).show();
            cloudViewModel.getMessageMutableLiveData().postValue(null);
        });

        binding.folders.setLayoutManager(new LinearLayoutManager(getContext()));

        CloudFolderAdapter cloudFolderAdapter = new CloudFolderAdapter(new ArrayList<>(), (val) -> {
            uploadFile.launch("*/*");
            cloudViewModel.setCurrentFolder(val);
        });

        cloudFolderAdapter.setOnDeleteFolderListener(cloudViewModel::deleteFolder);
        cloudFolderAdapter.setOnDeleteFileListener(cloudViewModel::deleteFile);
        cloudFolderAdapter.setCloudFileConsumer(cloudViewModel::openFile);

        cloudViewModel.getOpenFileMutableLiveData().observe(getViewLifecycleOwner(), (data) -> {
            if (data == null)
                return;
            openFile(data);
            cloudViewModel.getOpenFileMutableLiveData().postValue(null);
        });
        binding.folders.setAdapter(cloudFolderAdapter);

        initButtons();
        initObservers();
        return root;
    }

    public void openFile(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setData(uri);
//        intent.setDataAndType(uri, "*/*");
        startActivity(intent);
    }

    private void initObservers() {
        appSharedViewModel.getCloudFolderMutableLiveData().observe(getViewLifecycleOwner(), (data) -> {
            ((CloudFolderAdapter) binding.folders.getAdapter()).update(data);
        });
    }

    private void initButtons() {
        binding.addFolder.setOnClickListener((view) -> {
            new CreateFolderDialog(requireActivity(), (val) -> {
                cloudViewModel.createFolder(val);
            }).show();
        });
    }

    private ActivityResultLauncher<String> uploadFile = registerForActivityResult(new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null)
                    cloudViewModel.uploadFile(uri);
            });



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}