package edu.mirea.ardyc.umirea.data.dataSources;

import android.content.Context;

public abstract class DataSource<T> {
    public abstract T loadData();

    protected Context context;

    public DataSource(Context context) {
        this.context = context;
    }
}
