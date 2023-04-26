package edu.mirea.ardyc.umirea.ui.viewModel;

import android.app.Application;

import edu.mirea.ardyc.umirea.ui.viewModel.dashboard.DashboardService;
import edu.mirea.ardyc.umirea.ui.viewModel.group.GroupProcessor;

public class UmireaApplication extends Application {

    private DashboardService dashboardService = DashboardService.create(this);
    private GroupProcessor groupProcessor = GroupProcessor.create(this);

    public DashboardService getDashboardProcessor() {
        return dashboardService;
    }

    public GroupProcessor getGroupProcessor() {
        return groupProcessor;
    }
}
