package edu.mirea.ardyc.umirea.data.dataSources.cloud;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.mirea.ardyc.umirea.data.model.cloud.CloudFile;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;

public class MemoryCloudDataSource extends CloudDataSource {
    public MemoryCloudDataSource(Context context) {
        super(context);
    }

    @Override
    public List<CloudFolder> loadData() {
        List<CloudFolder> folders = new ArrayList<>();
        CloudFile cloudFile = new CloudFile(UUID.randomUUID().toString(), "Самостоятельная работа по Математичесскому анализу.docx", "/test", "mat.docx");
        CloudFolder cloudFolder = new CloudFolder(UUID.randomUUID().toString(), "Математический анализ");

        cloudFolder.getFiles().add(cloudFile);
        folders.add(cloudFolder);
        folders.add(new CloudFolder(UUID.randomUUID().toString(), "Test"));
        folders.add(new CloudFolder(UUID.randomUUID().toString(), "Test2"));

        return folders;
    }

    @Override
    public CloudFile loadFile(String fileName) {
        return null;
    }
}
