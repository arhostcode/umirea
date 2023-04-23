package edu.mirea.ardyc.umirea.data.repository.impl.cloud;

import android.content.Context;

import java.util.List;

import edu.mirea.ardyc.umirea.data.dataSources.cloud.LocalCloudDataSource;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;

public class CloudLocalRepository extends CloudRepository {

    private LocalCloudDataSource dataSource;

    public CloudLocalRepository(Context context) {
        super(context);
        this.dataSource = new LocalCloudDataSource(context);
    }

    @Override
    public List<CloudFolder> getData() {
        return dataSource.loadData();
    }
}
