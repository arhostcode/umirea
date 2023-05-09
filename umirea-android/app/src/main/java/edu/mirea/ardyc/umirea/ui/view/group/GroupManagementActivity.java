package edu.mirea.ardyc.umirea.ui.view.group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import dagger.hilt.android.AndroidEntryPoint;
import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.ui.viewModel.group.GroupManagementViewModel;

@AndroidEntryPoint
public class GroupManagementActivity extends AppCompatActivity {

    private GroupManagementViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(GroupManagementViewModel.class);
        setContentView(R.layout.activity_group_management);
    }



}