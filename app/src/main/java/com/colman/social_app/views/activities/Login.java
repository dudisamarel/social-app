package com.colman.social_app.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.colman.social_app.R;
import com.colman.social_app.SocialApplication;
import com.colman.social_app.ViewModelFactory;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText email;
    EditText password;

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModelFactory factory = ((SocialApplication) getApplication()).getViewModelFactory();
        viewModel = new ViewModelProvider(this,factory).get(LoginViewModel.class);

        //startActivity(new Intent(this, MainActivity.class));

        setContentView(R.layout.activity_login);
        email = findViewById(R.id.emailET);
        password = findViewById(R.id.passwordET);
        TextView signUpButton = findViewById(R.id.signupTV);
        Button loginBtn = findViewById(R.id.loginBtn);
        mAuth = FirebaseAuth.getInstance();
        loginBtn.setOnClickListener(v -> {
            login();
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

    private void login() {
        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();
        if (emailString.isEmpty()) {
            email.setError("Please enter email");
            email.requestFocus();
        } else if (passwordString.isEmpty()) {
            password.setError("Please enter password");
            password.requestFocus();
        } else
            mAuth.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    viewModel.setCurrUserEmail(emailString);
                    startActivity(new Intent(this, MainActivity.class));
                } else {
                    Toast.makeText(this, "Invalid email address or password", Toast.LENGTH_SHORT).show();
                }
            });
    }
}


