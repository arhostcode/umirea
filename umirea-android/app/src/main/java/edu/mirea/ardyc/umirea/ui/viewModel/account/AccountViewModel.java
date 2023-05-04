package edu.mirea.ardyc.umirea.ui.viewModel.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.mirea.ardyc.umirea.ui.viewModel.auth.UserService;
import edu.mirea.ardyc.umirea.ui.viewModel.dashboard.DashboardService;
import edu.mirea.ardyc.umirea.ui.viewModel.group.GroupService;

@HiltViewModel
public class AccountViewModel extends ViewModel {

    private UserService userService;
    private GroupService groupService;
    private DashboardService dashboardService;

    @Inject
    public AccountViewModel(UserService userService, GroupService groupService, DashboardService dashboardService) {
        this.userService = userService;
        this.groupService = groupService;
        this.dashboardService = dashboardService;
    }

    public void removeUserData() {
        new Thread(() -> {
            userService.removeUser();
            groupService.removeGroup();
            dashboardService.removeDashboard();
        }).start();

    }


}