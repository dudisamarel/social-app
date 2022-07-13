package com.colman.social_app.views.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.colman.social_app.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.util.Log;

import androidx.navigation.ui.AppBarConfiguration;


import com.colman.social_app.entities.User;
import com.colman.social_app.services.utils.ImageUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    FirebaseAuth mAuth;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
//        mAuth.signOut();
        FirebaseUser user = mAuth.getCurrentUser();
        ImageUtils imageUtils = new ImageUtils(this);
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);

        if (user == null) {
        } else {
//            imageUtils.SelectImage();
            Log.d(TAG, "onCreate: " + user.getEmail());
        }

    }


}