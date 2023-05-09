package edu.mirea.ardyc.umirea.data.dataSources;

import android.content.Context;

public abstract class DataSource {

    protected Context context;

    public DataSource(Context context) {
        this.context = context;
    }
}
