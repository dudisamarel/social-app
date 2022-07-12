package com.colman.social_app.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.colman.social_app.R;
import com.colman.social_app.services.UsersFirebase;

public class Login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText email = findViewById(R.id.emailET);
        EditText password = findViewById(R.id.passwordET);
        TextView signUpButton = findViewById(R.id.signupTV);
        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(v -> {
            UsersFirebase.getInstance().login(this, email.getText().toString(), password.getText().toString());
        });
        signUpButton.setOnClickListener(v -> {
            v.getContext().startActivity(new Intent(v.getContext(), Register.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }
}