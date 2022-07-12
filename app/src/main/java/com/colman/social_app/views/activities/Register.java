package com.colman.social_app.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.colman.social_app.R;
import com.colman.social_app.entities.User;
import com.colman.social_app.services.UsersFirebase;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText email = findViewById(R.id.emailET);
        EditText fullName = findViewById(R.id.full_nameET);
        EditText password = findViewById(R.id.passwordET);
        Button signUpBtn = findViewById(R.id.signupBtn);
        TextView signInTV = findViewById(R.id.signInTV);
        signUpBtn.setOnClickListener((View v) -> {
                UsersFirebase.getInstance().register(this, new User(email.getText().toString(), password.getText().toString(), fullName.getText().toString(), null));
        });
        signInTV.setOnClickListener(v -> {
            startActivity(new Intent(this, Login.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }
}