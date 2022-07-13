package com.colman.social_app.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.colman.social_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.Objects;

public class Register extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText email;
    EditText fullName;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.emailET);
        fullName = findViewById(R.id.full_nameET);
        password = findViewById(R.id.passwordET);
        Button signUpBtn = findViewById(R.id.signupBtn);
        TextView signInTV = findViewById(R.id.signInTV);
        mAuth = FirebaseAuth.getInstance();
        signUpBtn.setOnClickListener((View v) -> {
            createUser();
        });
        signInTV.setOnClickListener(v -> {
            startActivity(new Intent(this, Login.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }

    private void createUser() {
        String emailString = email.getText().toString();
        String fullNameString = fullName.getText().toString();
        String passwordString = password.getText().toString();
        if (fullNameString.isEmpty()) {
            fullName.setError("Please enter your full name");
            fullName.requestFocus();
        } else if (emailString.isEmpty()) {
            email.setError("Please enter email");
            email.requestFocus();
        } else if (passwordString.isEmpty()) {
            password.setError("Please enter password");
            password.requestFocus();
        } else
            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Created Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                } else {
                    try {
                        throw Objects.requireNonNull(task.getException());
                    } catch (FirebaseAuthWeakPasswordException e) {
                        password.setError("Password needs to contain at least 6 characters");
                        password.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        email.setError("Invalid email");
                        email.requestFocus();
                    } catch (FirebaseAuthUserCollisionException e) {
                        email.setError("Email Already exists");
                        email.requestFocus();
                        email.requestFocus();
                    } catch (Exception e) {
                        Toast.makeText(this, "Some error occurred, please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}


