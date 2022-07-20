package com.colman.social_app.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.colman.social_app.R;
import com.colman.social_app.SocialApplication;
import com.colman.social_app.ViewModelFactory;
import com.colman.social_app.entities.Post;
import com.colman.social_app.fragments.feedfragment.FeedFragment;
import com.colman.social_app.fragments.feedfragment.FeedFragmentDirections;
import com.colman.social_app.fragments.postDetailFragment.PostDetailsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements FeedFragment.SelectionListener {
    BottomNavigationView bottomNavigationView;
    NavController navController;
    MainActivityViewModel viewModel;
    PostDetailsFragment detailsFragment;
    LinearLayout detailsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelFactory factory = ((SocialApplication) this.getApplication()).getViewModelFactory();
        viewModel = new ViewModelProvider(this, factory).get(MainActivityViewModel.class);
        if (!viewModel.isLoggedIn()) {
            Intent intent = new Intent(this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        setContentView(R.layout.activity_main);
        detailsContainer = findViewById(R.id.details_container);
        detailsFragment = (PostDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_preview);
        initNavigation();
        initNavBar();

    }


    private void initNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        navController = navHostFragment.getNavController();
        navController.enableOnBackPressed(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        fragment = fragment.getChildFragmentManager().getFragments().get(0);
        fragment.onActivityResult(requestCode, resultCode, data);
    }


    protected void initNavBar() {
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.profile:
                    if (detailsContainer != null) {
                        param.weight = 0;
                        detailsContainer.setLayoutParams(param);
                    }
                    navController.navigate(R.id.action_global_userProfile);
                    return true;
                case R.id.home:
                    if (detailsContainer != null) {
                        param.weight = 2;
                        detailsContainer.setLayoutParams(param);
                    }
                    navController.navigate(R.id.action_global_feedFragment);
                    return true;
            }
            return false;
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void clickListener(View itemView, Post post, boolean showDetails) {
        // if clicked post belong to curr user
        if (detailsFragment == null) {
            if (showDetails) {
                Navigation.findNavController(itemView).navigate(
                        FeedFragmentDirections.actionFeedFragmentToAddPostFragment(post.getId())
                );
            } else { // if clicked post belong to other user
                Navigation.findNavController(itemView).navigate(
                        FeedFragmentDirections.actionFeedFragmentToPostDetailsFragment(post.getId())
                );
            }
        } else {
            if (showDetails) {
                Navigation.findNavController(itemView).navigate(
                        FeedFragmentDirections.actionFeedFragmentToAddPostFragment(post.getId())
                );
            } else {
                detailsFragment.setPostData(post);
            }
        }
    }
}