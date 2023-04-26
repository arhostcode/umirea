package edu.mirea.ardyc.umirea.data.dataSources.cloud;

import android.content.Context;

import java.util.List;

import edu.mirea.ardyc.umirea.data.dataSources.DataSource;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFile;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;

public abstract class CloudDataSource extends DataSource<List<CloudFolder>> {
    public CloudDataSource(Context context) {
        super(context);
    }

    public abstract CloudFile loadFile(String fileName);
}
