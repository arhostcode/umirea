package edu.mirea.ardyc.umirea.ui.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import edu.mirea.ardyc.umirea.R;

public class MainActivity extends AppCompatActivity {

    public static String APP_TAG_LOGS = "UMIREA_LOGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}