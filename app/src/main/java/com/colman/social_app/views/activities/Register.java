package com.colman.social_app.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.colman.social_app.R;

public class Register extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//
//        EditText email = findViewById(R.id.emailET);
//        EditText password = findViewById(R.id.passwordET);
//        TextView signUpButton = findViewById(R.id.signupTV);
//        signUpButton.setOnClickListener(v -> {
//            startActivity(new Intent(this,Register.class));
//        });
    }
}