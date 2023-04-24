package edu.mirea.ardyc.umirea.ui.viewModel.cloud;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.net.URLDecoder;
import java.util.List;
import java.util.UUID;

import edu.mirea.ardyc.umirea.data.model.cloud.CloudFile;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;

public class CloudViewModel extends AndroidViewModel {

    private MutableLiveData<List<CloudFolder>> cloudMutableLiveData;

    private String currentFolder;

    public CloudViewModel(@NonNull Application application) {
        super(application);
    }

    public void setCurrentFolder(String currentFolder) {
        this.currentFolder = currentFolder;
    }

    public void uploadFile(String uri) {
        List<CloudFolder> folders = cloudMutableLiveData.getValue();
        for (CloudFolder folder : folders) {
            if (folder.getUuid().equals(currentFolder)) {
                try {
                    String[] contentPath = URLDecoder.decode(uri, "utf-8").split(":");
                    String[] path = contentPath[contentPath.length - 1].split("/");
                    String name = path[path.length - 1];
                    folder.getFiles().add(new CloudFile(UUID.randomUUID().toString(), name, "", ""));
                    cloudMutableLiveData.postValue(folders);
                } catch (Exception e) {
                    return;
                }
                break;
            }
        }
    }

    public void createFolder(String name) {
        List<CloudFolder> folders = cloudMutableLiveData.getValue();
        folders.add(new CloudFolder(UUID.randomUUID().toString(), name));
        cloudMutableLiveData.postValue(folders);
    }

    public MutableLiveData<List<CloudFolder>> getCloudMutableLiveData() {
        return cloudMutableLiveData;
    }

    public void setCloudMutableLiveData(MutableLiveData<List<CloudFolder>> cloudMutableLiveData) {
        this.cloudMutableLiveData = cloudMutableLiveData;
    }
}