package com.colman.social_app.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.colman.social_app.R;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText email;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                } else {
                    Toast.makeText(this, "Invalid email address or password", Toast.LENGTH_SHORT).show();
                }
            });
    }
}


