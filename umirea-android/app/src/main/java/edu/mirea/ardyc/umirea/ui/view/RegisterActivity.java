package edu.mirea.ardyc.umirea.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import edu.mirea.ardyc.umirea.databinding.ActivityRegisterBinding;
import edu.mirea.ardyc.umirea.ui.viewModel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        registerViewModel.init();
        binding.verify.setOnClickListener(view -> registerViewModel.verify(binding.loginText.getText().toString(), binding.passwordText.getText().toString(), binding.firstName.getText().toString(), binding.lastName.getText().toString()));
        binding.enterButton.setOnClickListener(view -> {
            if (binding.code.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Получите код подтверждения на почту. Проверьте папку спам.", Toast.LENGTH_SHORT).show();
            } else
                registerViewModel.register(binding.loginText.getText().toString(), binding.passwordText.getText().toString(), binding.firstName.getText().toString(), binding.lastName.getText().toString(), binding.code.getText().toString());
        });

        registerViewModel.getErrorText().observe(this, s -> Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show());


        setContentView(binding.getRoot());
    }
}