package edu.mirea.ardyc.umirea.ui.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.databinding.ActivityLoginBinding;
import edu.mirea.ardyc.umirea.ui.viewModel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding activityLoginBinding;
    private LoginViewModel loginViewModel;

    private final float MIN_LAYOUT_WEIGHT = 3f;
    private final float MAX_LAYOUT_WEIGHT = 50f;

    private final int MIN_ICON_SIZE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.init();
        setContentView(activityLoginBinding.getRoot());
        activityLoginBinding.enterButton.setOnClickListener(view -> {
            loginViewModel.loginToServer(activityLoginBinding.loginText.getText().toString(), activityLoginBinding.passwordText.getText().toString());
        });
        activityLoginBinding.createAccount.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });
        loginViewModel.getErrorText().observe(this, s -> Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show());
        loginViewModel.getUserMutableLiveData().observe(this, s -> {
            startActivity(new Intent(LoginActivity.this, AppActivity.class));
            finish();
        });

        animateInitialization();

    }


    private void animateInitialization() {
        ValueAnimator anim = ValueAnimator.ofFloat(MAX_LAYOUT_WEIGHT * 2, MIN_LAYOUT_WEIGHT * 2);
        anim.addUpdateListener(valueAnimator -> {
            float val = (Float) valueAnimator.getAnimatedValue();
            setIconImageParams(val);
            setInputLayoutWeight(val / 2);
            if (val <= MIN_LAYOUT_WEIGHT * 2) {
                animateAlpha();
            }
        });
        anim.setDuration(2000);
        anim.start();
    }

    private void setIconImageParams(float val) {
        ViewGroup.LayoutParams layoutParams = activityLoginBinding.imageView.getLayoutParams();
        layoutParams.width = (int) (MIN_ICON_SIZE + val / 2);
        layoutParams.height = (int) (MIN_ICON_SIZE + val / 2);
        activityLoginBinding.imageView.setLayoutParams(layoutParams);
    }

    private void setInputLayoutWeight(float weight) {
        ((LinearLayout.LayoutParams) activityLoginBinding.inputLayout.getLayoutParams()).weight = weight;
    }

    private void animateAlpha() {
        ValueAnimator alphaAnimation = ValueAnimator.ofFloat(0f, 1f);
        alphaAnimation.addUpdateListener(alphaAnimator -> {
            float alpha = (Float) alphaAnimation.getAnimatedValue();
            activityLoginBinding.textLayout.setAlpha(alpha);
            activityLoginBinding.inputLayout.setAlpha(alpha);
        });
        alphaAnimation.setDuration(1000);
        alphaAnimation.start();
    }
}