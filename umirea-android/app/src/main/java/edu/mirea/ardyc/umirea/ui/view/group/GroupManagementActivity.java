package edu.mirea.ardyc.umirea.ui.view.group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import dagger.hilt.android.AndroidEntryPoint;
import edu.mirea.ardyc.umirea.R;

@AndroidEntryPoint
public class GroupManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_management);
    }

}