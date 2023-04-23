package edu.mirea.ardyc.umirea.ui.view;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarMenuView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import java.util.Map;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.databinding.ActivityAppBinding;
import edu.mirea.ardyc.umirea.ui.view.home.HomeFragment;
import edu.mirea.ardyc.umirea.ui.viewModel.AppSharedViewModel;
import edu.mirea.ardyc.umirea.ui.viewModel.AppViewModel;

public class AppActivity extends AppCompatActivity {

    private ActivityAppBinding binding;
    private AppViewModel appViewModel;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAppBinding.inflate(getLayoutInflater());
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        setContentView(binding.getRoot());

        navController = Navigation.findNavController(this, R.id.navFragment);
        NavigationUI.setupWithNavController(binding.navView, navController);
        setupNavbarLongClick();
        AppSharedViewModel appSharedViewModel = new ViewModelProvider(this).get(AppSharedViewModel.class);

        appViewModel.getChatMutableLiveData().observe(this, (val) -> appSharedViewModel.getChatMutableLiveData().postValue(val));
        appViewModel.getTimetableMutableLiveData().observe(this, (val) -> {
            appSharedViewModel.getTimetableMutableLiveData().postValue(val);
        });
        appViewModel.getGroupMutableLiveData().observe(this, (val) -> appSharedViewModel.getGroupMutableLiveData().postValue(val));
        appViewModel.getCloudFolderMutableLiveData().observe(this, (val) -> appSharedViewModel.getCloudFolderMutableLiveData().postValue(val));

        Snackbar.make(this, binding.getRoot(), "Приложение использует тестовые данные", BaseTransientBottomBar.LENGTH_LONG).show();
    }

    private void setupNavbarLongClick() {
        NavigationBarMenuView menuView = (NavigationBarMenuView) binding.navView.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            NavigationBarItemView item = (NavigationBarItemView) menuView.getChildAt(i);
            item.setOnLongClickListener((v) -> {
                if (v.getId() == R.id.navigation_home && v.isSelected() && navController.getBackQueue().last().getDestination().getId() == R.id.navigation_home) {
                    navController.navigate(R.id.action_navigation_home_to_navigation_info);
                }
                return true;
            });
        }

    }


}