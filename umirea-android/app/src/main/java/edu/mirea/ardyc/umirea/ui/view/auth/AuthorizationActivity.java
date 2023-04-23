package edu.mirea.ardyc.umirea.ui.view.auth;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.databinding.ActivityAuthorizationBinding;


public class AuthorizationActivity extends AppCompatActivity {

    private ActivityAuthorizationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAuthorizationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

}