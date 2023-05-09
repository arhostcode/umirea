package edu.mirea.ardyc.umirea.data.dataSources.cloud;

import android.content.Context;

import java.util.List;
import java.util.stream.Collectors;

import edu.mirea.ardyc.umirea.data.dataSources.DataSource;
import edu.mirea.ardyc.umirea.data.dataSources.room.UmireaDatabase;
import edu.mirea.ardyc.umirea.data.dataSources.room.cloud.entities.FileWithFolder;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;
import edu.mirea.ardyc.umirea.data.model.DataResponse;

public class CloudDataSource extends DataSource {
    public CloudDataSource(Context context) {
        super(context);
    }

    public void saveData(List<CloudFolder> cloudFolders) {
        UmireaDatabase.getDatabase(context).cloudFileDao().updateFiles(cloudFolders);
    }

    public DataResponse<List<CloudFolder>> getData() {
        return new DataResponse<>(UmireaDatabase.getDatabase(context).cloudFileDao().getFolders().stream().map(FileWithFolder::toModel).collect(Collectors.toList()));
    }

    public void clear() {
        UmireaDatabase.getDatabase(context).cloudFileDao().clearAll();
    }
}
