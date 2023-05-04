package edu.mirea.ardyc.umirea.ui.viewModel.cloud;

import android.content.Context;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;
import edu.mirea.ardyc.umirea.data.model.net.DataResponse;
import edu.mirea.ardyc.umirea.data.repository.impl.cloud.CloudRepository;

public class CloudService {

    private Context context;

    private CloudRepository cloudRepository;

    @Inject
    public CloudService(Context context, CloudRepository cloudRepository) {
        this.context = context;
        this.cloudRepository = cloudRepository;
    }

    public DataResponse<List<CloudFolder>> getFolders(String userToken) {
        DataResponse<List<CloudFolder>> response = cloudRepository.getData(userToken);
        return response;
    }

    public DataResponse<List<CloudFolder>> loadLocalFolders() {
        return cloudRepository.loadLocalFolders();
    }

    public void saveFolders(List<CloudFolder> folders) {
        cloudRepository.saveData(folders);
    }

    public DataResponse<List<CloudFolder>> uploadFileInFolder(String folderUuid, String userToken, File file) {
        return cloudRepository.uploadFileInFolder(folderUuid, userToken, file);
    }

    public DataResponse<InputStream> downloadFile(String fileUuid, String userToken) {
        return cloudRepository.downloadFile(fileUuid, userToken);
    }
}
