package edu.mirea.ardyc.umirea.data.repository;

import android.content.Context;

public class RemoteRepository<T> extends Repository<T> {
    public RemoteRepository(Context context) {
        super(context);
    }

    @Override
    public T getData() {
        return null;
    }
}
