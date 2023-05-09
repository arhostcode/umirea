package edu.mirea.ardyc.umirea.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;

import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarMenuView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import dagger.hilt.android.AndroidEntryPoint;
import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.databinding.ActivityAppBinding;
import edu.mirea.ardyc.umirea.ui.view.auth.AuthorizationActivity;
import edu.mirea.ardyc.umirea.ui.viewModel.AppSharedViewModel;
import edu.mirea.ardyc.umirea.ui.viewModel.AppViewModel;
import es.dmoral.toasty.Toasty;

@AndroidEntryPoint
public class AppActivity extends AppCompatActivity {

    public static String APP_PATH = "edu.mirea.ardyc.umirea";

    private ActivityAppBinding binding;
    private AppViewModel appViewModel;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toasty.Config.getInstance().setGravity(Gravity.TOP, 0, 20).apply();
        binding = ActivityAppBinding.inflate(getLayoutInflater());
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        setContentView(binding.getRoot());

        AppSharedViewModel appSharedViewModel = new ViewModelProvider(this).get(AppSharedViewModel.class);

        appViewModel.getChatMutableLiveData().observe(this, (val) -> appSharedViewModel.getChatMutableLiveData().postValue(val));
        appViewModel.getTimetableMutableLiveData().observe(this, (val) -> appSharedViewModel.getTimetableMutableLiveData().postValue(val));
        appViewModel.getGroupMutableLiveData().observe(this, (val) -> appSharedViewModel.getGroupMutableLiveData().postValue(val));
        appViewModel.getCloudFolderMutableLiveData().observe(this, (val) -> {
            appSharedViewModel.getCloudFolderMutableLiveData().postValue(val);
        });
        appViewModel.getInfoMessages().observe(this, (val) -> appSharedViewModel.getListInfoMutableLiveData().postValue(val));
        appViewModel.getUserMutableLiveData().observe(this, (val) -> {
            if (val == null) {
                startActivity(new Intent(this, AuthorizationActivity.class));
                finish();
                return;
            }

            if (val.getToken().isEmpty())
                return;

            if (val.getEducationGroup().equals("null")) {
                startActivity(new Intent(this, AuthorizationActivity.class));
                finish();
                return;
            }
            showMessage("Обновление данных");
            appViewModel.initModules(val);
            appSharedViewModel.getUserMutableLiveData().postValue(val);
        });
        appViewModel.getErrorMessage().observe(this, (val) -> {
            if (val == null || val.isEmpty())
                return;
            showMessage(val);
            appViewModel.getErrorMessage().postValue("");
        });
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

    @Override
    protected void onStart() {
        super.onStart();
        navController = Navigation.findNavController(this, R.id.navFragment);
        NavigationUI.setupWithNavController(binding.navView, navController);
        setupNavbarLongClick();

    }

    private void showMessage(String message) {
        Toasty.info(getApplicationContext(), message).show();
    }

    @Override
    protected void onDestroy() {
        appViewModel.shutdown();
        super.onDestroy();
    }
}