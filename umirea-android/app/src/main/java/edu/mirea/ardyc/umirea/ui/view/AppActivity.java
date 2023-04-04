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
        NavController navController = Navigation.findNavController(this, R.id.navFragment);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}