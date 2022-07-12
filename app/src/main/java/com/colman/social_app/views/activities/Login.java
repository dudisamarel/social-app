package com.colman.social_app.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.colman.social_app.R;

public class Login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText email = findViewById(R.id.emailET);
        EditText password = findViewById(R.id.passwordET);
        TextView signUpButton = findViewById(R.id.signupTV);

        signUpButton.setOnClickListener(v -> {
           v.getContext().startActivity(new Intent(v.getContext(),Register.class));
        });
    }
}