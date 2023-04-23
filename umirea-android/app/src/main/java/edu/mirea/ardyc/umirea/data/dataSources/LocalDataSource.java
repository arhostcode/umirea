package edu.mirea.ardyc.umirea.data.dataSources;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

public class LocalDataSource<T> extends DataSource<MutableLiveData<T>> {
    public LocalDataSource(Context context) {
        super(context);
    }

    @Override
    public MutableLiveData<T> loadData() {
        return null;
    }
}
