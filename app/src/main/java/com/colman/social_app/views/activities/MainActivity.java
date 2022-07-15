package com.colman.social_app.views.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.colman.social_app.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        findViewById(R.id.icon).setOnClickListener(e -> switchToMainFragment());
//        switchToMainFragment();
        initNavigation();
        initNavBar();
    }


//    public void loadFragment(Fragment fragment, boolean shouldAddToBacKStack, boolean shouldEmptyAllStack) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        if (shouldEmptyAllStack) {
//            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        }
//        transaction.replace(R.id.fragment_container, fragment);
//        if (shouldAddToBacKStack) {
//            transaction.addToBackStack(null);
//        }
//        transaction.commit();
//    }

    private void initNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        navController = navHostFragment.getNavController();

    }

    protected void initNavBar() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.profile:
                    navController.navigate(R.id.action_global_userProfile);
                    return true;
                case R.id.home:
                    navController.navigate(R.id.action_global_feedFragment);
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