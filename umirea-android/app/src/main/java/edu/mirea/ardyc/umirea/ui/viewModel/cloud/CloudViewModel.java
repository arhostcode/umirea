package edu.mirea.ardyc.umirea.ui.viewModel.cloud;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.mirea.ardyc.umirea.BuildConfig;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFile;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;
import edu.mirea.ardyc.umirea.data.model.DataResponse;
import edu.mirea.ardyc.umirea.data.repository.impl.cloud.CloudRepository;
import edu.mirea.ardyc.umirea.ui.view.AppActivity;
import edu.mirea.ardyc.umirea.ui.viewModel.AppViewModel;

@HiltViewModel
public class CloudViewModel extends AndroidViewModel {

    private MutableLiveData<List<CloudFolder>> cloudMutableLiveData;
    private MutableLiveData<String> messageMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Uri> openFileMutableLiveData = new MutableLiveData<>();
    private CloudRepository cloudRepository;

    private String currentFolder;

    @Inject
    public CloudViewModel(@NonNull Application application, CloudRepository cloudRepository) {
        super(application);
        this.cloudRepository = cloudRepository;
    }

    public void setCurrentFolder(String currentFolder) {
        this.currentFolder = currentFolder;
    }

    public void uploadFile(Uri uri) {
        new Thread(() -> {
            try {
                String fileName = getFileName(uri);
                File folder = new File(getApplication().getFilesDir(), currentFolder);
                if (!folder.exists()) {
                    folder.mkdir();
                }
                File file = new File(folder, fileName);
                if (!file.exists()) {
                    file.createNewFile();
                }
                InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);
                FileOutputStream outputStream = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int len;
                while ((len = inputStream.read(buf)) > 0) {
                    outputStream.write(buf, 0, len);
                }
                inputStream.close();
                outputStream.close();
                String userToken = getApplication().getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_token", "");
                DataResponse<List<CloudFolder>> cloudFolders = cloudRepository.uploadFileInFolder(currentFolder, userToken, file);
                if (cloudFolders.isError())
                    return;
                cloudMutableLiveData.postValue(cloudFolders.getData());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void openFile(CloudFile cloudFile) {
        new Thread(() -> {
            String userToken = getApplication().getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_token", "");
            File folder = new File(getApplication().getFilesDir(), cloudFile.getFolderId());
            File file = new File(folder, cloudFile.getName());
            if (!file.exists()) {
                DataResponse<InputStream> dataResponse = cloudRepository.downloadFile(cloudFile.getUuid(), userToken);
                if (dataResponse.isError()) {
                    messageMutableLiveData.postValue("Ошибка загрузки файла");
                    return;
                }
                try {
                    if (!folder.exists())
                        folder.mkdir();
                    if (!file.exists())
                        file.createNewFile();
                    FileOutputStream outputStream = new FileOutputStream(file);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = dataResponse.getData().read(buf)) > 0) {
                        outputStream.write(buf, 0, len);
                    }
                    dataResponse.getData().close();
                    outputStream.close();
                } catch (IOException e) {
                    messageMutableLiveData.postValue("Ошибка загрузки файла");
                    return;
                }
            }
            Uri uri = FileProvider.getUriForFile(getApplication(), BuildConfig.APPLICATION_ID, file);
            getOpenFileMutableLiveData().postValue(uri);
        }).start();
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getApplication().getContentResolver().query(uri, null, null, null, null)) {
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

    public void createFolder(String name) {
        currentFolder = name;
        AppViewModel.executorService.execute(() -> {
            String userToken = getApplication().getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_token", "");
            DataResponse<CloudFolder> cloudFolderDataResponse = cloudRepository.createFolder(userToken, name);
            if (cloudFolderDataResponse.isError()) {
                messageMutableLiveData.postValue(cloudFolderDataResponse.getMessage());
                return;
            }
            DataResponse<List<CloudFolder>> cloudFolders = cloudRepository.getData(userToken);
            if (cloudFolderDataResponse.isError()) {
                messageMutableLiveData.postValue(cloudFolderDataResponse.getMessage());
                return;
            }
            cloudMutableLiveData.postValue(cloudFolders.getData());

        });
    }

    public MutableLiveData<List<CloudFolder>> getCloudMutableLiveData() {
        return cloudMutableLiveData;
    }

    public void setCloudMutableLiveData(MutableLiveData<List<CloudFolder>> cloudMutableLiveData) {
        this.cloudMutableLiveData = cloudMutableLiveData;
    }

    public MutableLiveData<Uri> getOpenFileMutableLiveData() {
        return openFileMutableLiveData;
    }

    public MutableLiveData<String> getMessageMutableLiveData() {
        return messageMutableLiveData;
    }
}