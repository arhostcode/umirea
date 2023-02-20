package edu.mirea.ardyc.umirea.ui.view;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import edu.mirea.ardyc.umirea.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding activityLoginBinding;

    private final float MIN_LAYOUT_WEIGHT = 3f;
    private final float MAX_LAYOUT_WEIGHT = 50f;

    private final int MIN_ICON_SIZE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());
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

        activityLoginBinding.enterButton.setOnClickListener((v) -> {
            startActivity(new Intent(LoginActivity.this, AppActivity.class));
        });
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