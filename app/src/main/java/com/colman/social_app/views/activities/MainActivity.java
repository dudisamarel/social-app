package com.colman.social_app.views.activities;

import android.content.Intent;
import android.os.Bundle;

import com.colman.social_app.R;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import androidx.navigation.ui.AppBarConfiguration;


import com.colman.social_app.entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private final String TAG = "MainActivity";
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        } else {
            Log.d(TAG, "onCreate: " + user.getEmail());
        }
    }

}