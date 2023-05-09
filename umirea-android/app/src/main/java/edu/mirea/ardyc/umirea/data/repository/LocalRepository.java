package edu.mirea.ardyc.umirea.data.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

public abstract class LocalRepository<T> extends Repository {
    public LocalRepository(Context context) {
        super(context);
    }

}
