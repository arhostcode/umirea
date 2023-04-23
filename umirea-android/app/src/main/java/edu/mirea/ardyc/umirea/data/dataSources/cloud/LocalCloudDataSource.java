package edu.mirea.ardyc.umirea.data.dataSources.cloud;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import edu.mirea.ardyc.umirea.data.model.cloud.CloudFile;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;

public class LocalCloudDataSource extends CloudDataSource {
    public LocalCloudDataSource(Context context) {
        super(context);
    }

    @Override
    public List<CloudFolder> loadData() {
        List<CloudFolder> folders = new ArrayList<>();
        CloudFile cloudFile = new CloudFile("Самостоятельная работа по Математичесскому анализу.docx", "/test", "mat.docx");
        CloudFolder cloudFolder = new CloudFolder("Математический анализ");

        cloudFolder.getFiles().add(cloudFile);
        folders.add(cloudFolder);
        folders.add(new CloudFolder("Test"));
        folders.add(new CloudFolder("Test"));
        folders.add(new CloudFolder("Test"));
        folders.add(new CloudFolder("Test"));
        folders.add(new CloudFolder("Test"));
        folders.add(new CloudFolder("Test"));
        folders.add(new CloudFolder("Test"));
        folders.add(new CloudFolder("Test"));
        folders.add(new CloudFolder("Test"));
        folders.add(new CloudFolder("Test"));
        folders.add(new CloudFolder("Test"));
        folders.add(new CloudFolder("Test"));

        return folders;
    }

    @Override
    public CloudFile loadFile(String fileName) {
        return null;
    }
}
