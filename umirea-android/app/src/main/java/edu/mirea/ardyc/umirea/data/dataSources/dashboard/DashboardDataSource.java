package edu.mirea.ardyc.umirea.data.dataSources.dashboard;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import edu.mirea.ardyc.umirea.data.dataSources.DataSource;
import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;

public abstract class DashboardDataSource extends DataSource {

    public DashboardDataSource(Context context) {
        super(context);
    }

}
