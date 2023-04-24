package edu.mirea.ardyc.umirea.ui.viewModel;

import android.app.Application;

import edu.mirea.ardyc.umirea.ui.viewModel.dashboard.DashboardProcessor;
import edu.mirea.ardyc.umirea.ui.viewModel.group.GroupProcessor;

public class UmireaApplication extends Application {

    private DashboardProcessor dashboardProcessor = DashboardProcessor.create(this);
    private GroupProcessor groupProcessor = GroupProcessor.create(this);

    public DashboardProcessor getDashboardProcessor() {
        return dashboardProcessor;
    }

    public GroupProcessor getGroupProcessor() {
        return groupProcessor;
    }
}
