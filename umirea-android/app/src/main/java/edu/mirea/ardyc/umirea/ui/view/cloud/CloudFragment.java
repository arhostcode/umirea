package edu.mirea.ardyc.umirea.ui.view.cloud;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;
import edu.mirea.ardyc.umirea.databinding.FragmentCloudBinding;
import edu.mirea.ardyc.umirea.ui.view.cloud.adapters.CloudFolderAdapter;
import edu.mirea.ardyc.umirea.ui.viewModel.AppSharedViewModel;
import edu.mirea.ardyc.umirea.ui.viewModel.cloud.CloudViewModel;

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

        binding.folders.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.folders.setAdapter(new CloudFolderAdapter(new ArrayList<>(), (val) -> {
            uploadFile.launch("*/*");
            cloudViewModel.setCurrentFolder(val);
        }));

        initButtons();
        initObservers();
        return root;
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
                    cloudViewModel.uploadFile(getFileName(uri));
            });
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            });

    private void requestPermission() {
//        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = requireActivity().getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int length = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    result = cursor.getString(Math.max(length, 0));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}