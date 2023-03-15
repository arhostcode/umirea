package edu.mirea.ardyc.umirea.ui.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.lang.reflect.Array;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.databinding.ActivityAppBinding;
import edu.mirea.ardyc.umirea.ui.viewModel.AppViewModel;

public class AppActivity extends AppCompatActivity {

    private ActivityAppBinding binding;
    private AppViewModel appViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAppBinding.inflate(getLayoutInflater());
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        setContentView(binding.getRoot());
        initFragments();


        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnItemSelectedListener(item -> {
                    Fragment newFragment = appViewModel.chooseFragment(item.getItemId());
                    displayFragment(newFragment);
                    return true;
                }
        );
        getSupportFragmentManager().beginTransaction().show(appViewModel.homeFragment);
        navView.setSelectedItemId(R.id.navigation_dashboard);
    }

    private void initFragments() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (Fragment f : appViewModel.getNavigationList()) {
            ft.add(R.id.navFragment, f);
            ft.hide(f);
        }
        ft.commit();
    }

    protected void displayFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (Fragment f : appViewModel.getNavigationList()) {
            if (f == fragment) {
                ft.show(f);
            } else {
                ft.hide(f);
            }
        }
        ft.commit();
    }

}