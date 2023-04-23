package edu.mirea.ardyc.umirea.data.repository.impl.cloud;

import android.content.Context;

import java.util.List;

import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;
import edu.mirea.ardyc.umirea.data.repository.Repository;

public abstract class CloudRepository extends Repository<List<CloudFolder>> {
    public CloudRepository(Context context) {
        super(context);
    }
}
