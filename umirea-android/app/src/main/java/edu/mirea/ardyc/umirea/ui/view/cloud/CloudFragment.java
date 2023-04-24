package edu.mirea.ardyc.umirea.ui.view.cloud;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cloudViewModel =
                new ViewModelProvider(this).get(CloudViewModel.class);

        binding = FragmentCloudBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        AppSharedViewModel appSharedViewModel = new ViewModelProvider(requireActivity()).get(AppSharedViewModel.class);
        cloudViewModel.setCloudMutableLiveData(appSharedViewModel.getCloudFolderMutableLiveData());
        binding.folders.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.folders.setAdapter(new CloudFolderAdapter(new ArrayList<>(), (val) -> {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            } else {
                uploadFile.launch("*/*");
                cloudViewModel.setCurrentFolder(val);
            }
        }));
        appSharedViewModel.getCloudFolderMutableLiveData().observe(getViewLifecycleOwner(), (data) -> {
            ((CloudFolderAdapter) binding.folders.getAdapter()).update(data);
        });
        binding.addFolder.setOnClickListener((view) -> {
            new CreateFolderDialog(requireActivity(), (val) -> {
                cloudViewModel.createFolder(val);
            }).show();
        });

        return root;
    }

    ActivityResultLauncher<String> uploadFile = registerForActivityResult(new ActivityResultContracts.GetContent(),
            uri -> {
                cloudViewModel.uploadFile(uri.toString());
            });

    private String readTextFromUri(Uri uri) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream =
                     requireActivity().getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}