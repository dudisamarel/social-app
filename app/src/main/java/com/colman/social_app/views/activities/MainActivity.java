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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;


import com.colman.social_app.entities.User;
import com.colman.social_app.fragments.UserProfile;
import com.colman.social_app.services.utils.ImageUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        findViewById(R.id.icon).setOnClickListener(e -> switchToMainFragment());
//        switchToMainFragment();
        initNavBar();
    }


    public void loadFragment(Fragment fragment, boolean shouldAddToBacKStack, boolean shouldEmptyAllStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (shouldEmptyAllStack) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        transaction.replace(R.id.fragment_container, fragment);
        if (shouldAddToBacKStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    protected void initNavBar() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.profile:
                    loadFragment(new UserProfile(),false ,true);
                    return true;
                case R.id.home:
                    return true;
            }
            return false;
        });

    }



//
//    public void switchToMainFragment() {
//        loadFragment(new MainFragment(), false, true);
//    }


}