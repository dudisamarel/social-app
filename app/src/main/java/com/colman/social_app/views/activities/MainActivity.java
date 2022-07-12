package com.colman.social_app.views.activities;

import android.content.Intent;
import android.os.Bundle;

import com.colman.social_app.R;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import androidx.navigation.ui.AppBarConfiguration;


import com.colman.social_app.entities.User;
import com.colman.social_app.services.UsersFirebase;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this.TAG, "Adding user");
        UsersFirebase.getInstance().add(MainActivity.this,new User("asdadsf@gmail.com", "aa", null, null));
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

}