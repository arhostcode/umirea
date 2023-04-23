package edu.mirea.ardyc.umirea.ui.viewModel;

import android.app.Application;

import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.ui.viewModel.dashboard.DashboardProcessor;
import edu.mirea.ardyc.umirea.ui.viewModel.group.GroupProcessor;

public class UmireaApplication extends Application {

    private DashboardProcessor dashboardProcessor = DashboardProcessor.getInstance(this);
    private GroupProcessor groupProcessor = GroupProcessor.getInstance(this);

    public DashboardProcessor getDashboardProcessor() {
        return dashboardProcessor;
    }

    public GroupProcessor getGroupProcessor() {
        return groupProcessor;
    }
}
