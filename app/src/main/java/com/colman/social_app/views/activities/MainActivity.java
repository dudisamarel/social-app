package com.colman.social_app.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
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
        Log.i("POST_CLICK", post.getTitle());
    }
}