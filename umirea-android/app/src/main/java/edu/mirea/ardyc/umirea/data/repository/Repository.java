package edu.mirea.ardyc.umirea.data.repository;

import android.content.Context;

public abstract class Repository {

    protected Context context;

    public Repository(Context context) {
        this.context = context;
    }


}
