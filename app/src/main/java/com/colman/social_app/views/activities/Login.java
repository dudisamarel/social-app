package com.colman.social_app.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.colman.social_app.R;
import com.colman.social_app.SocialApplication;
import com.colman.social_app.ViewModelFactory;

public class Login extends AppCompatActivity {
    EditText emailET;
    EditText passwordET;

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelFactory factory = ((SocialApplication) getApplication()).getViewModelFactory();
        viewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);
        setContentView(R.layout.activity_login);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        TextView signUpButton = findViewById(R.id.signupTV);
        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(v -> {
            String email = emailET.getText().toString();
            String password = passwordET.getText().toString();
            if (email.isEmpty()) {
                emailET.setError("Please enter email");
                emailET.requestFocus();
            } else if (password.isEmpty()) {
                passwordET.setError("Please enter password");
                passwordET.requestFocus();
            }
            viewModel.login(email, password, task -> {
                if (task.isSuccessful()) {
                    viewModel.saveUser(email);
                    Toast.makeText(this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Invalid email address or password", Toast.LENGTH_SHORT).show();
                }
            });
        });
        signUpButton.setOnClickListener(v -> {
            v.getContext().startActivity(new Intent(v.getContext(), Register.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        return false;
    }
}


