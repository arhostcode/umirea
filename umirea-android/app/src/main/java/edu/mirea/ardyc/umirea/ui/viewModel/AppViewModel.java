package edu.mirea.ardyc.umirea.ui.viewModel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import edu.mirea.ardyc.umirea.ui.view.account.AccountFragment;
import edu.mirea.ardyc.umirea.ui.view.chat.ChatFragment;
import edu.mirea.ardyc.umirea.ui.view.cloud.CloudFragment;
import edu.mirea.ardyc.umirea.ui.view.dashboard.DashboardFragment;
import edu.mirea.ardyc.umirea.ui.view.home.HomeFragment;
import edu.mirea.ardyc.umirea.ui.view.info.InfoFragment;
import edu.mirea.ardyc.umirea.R;

public class AppViewModel extends ViewModel {

    public AccountFragment accountFragment;
    public DashboardFragment dashboardFragment;
    public ChatFragment chatFragment;
    public InfoFragment infoFragment;
    public HomeFragment homeFragment;
    public CloudFragment cloudFragment;

    private final HashMap<Integer, Fragment> navigationMap = new HashMap<>();

    public AppViewModel() {
        homeFragment = new HomeFragment();
        accountFragment = new AccountFragment();
        dashboardFragment = new DashboardFragment();
//        infoFragment = new InfoFragment();
        cloudFragment = new CloudFragment();
        chatFragment = new ChatFragment();

//        navigationMap.put(R.id.navigation_info, infoFragment);
        navigationMap.put(R.id.navigation_home, homeFragment);
        navigationMap.put(R.id.navigation_account, accountFragment);
        navigationMap.put(R.id.navigation_dashboard, dashboardFragment);
        navigationMap.put(R.id.navigation_chat, chatFragment);
        navigationMap.put(R.id.navigation_cloud, cloudFragment);
    }

    public Fragment chooseFragment(int fragmentId) {
        return navigationMap.get(fragmentId);
    }

    public List<Fragment> getNavigationList() {
        return new ArrayList<>(navigationMap.values());
    }
}
