package edu.mirea.ardyc.umirea.data.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

public abstract class LocalRepository<T> extends Repository<MutableLiveData<T>> {
    public LocalRepository(Context context) {
        super(context);
    }

    @Override
    public abstract MutableLiveData<T> getData();
}
