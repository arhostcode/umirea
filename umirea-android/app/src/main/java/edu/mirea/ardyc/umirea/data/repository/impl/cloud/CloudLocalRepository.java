package edu.mirea.ardyc.umirea.data.repository.impl.cloud;

import android.content.Context;

import java.util.List;

import edu.mirea.ardyc.umirea.data.dataSources.cloud.MemoryCloudDataSource;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;

public class CloudLocalRepository extends CloudRepository {

    private MemoryCloudDataSource dataSource;

    public CloudLocalRepository(Context context) {
        super(context);
        this.dataSource = new MemoryCloudDataSource(context);
    }

    @Override
    public List<CloudFolder> getData() {
        return dataSource.loadData();
    }
}
