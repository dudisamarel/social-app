package com.colman.social_app.fragments.feedfragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.colman.social_app.entities.Post;
import com.colman.social_app.repositories.SharedPreferencesRepo;
import com.colman.social_app.repositories.SocialAppDataBase;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PostsFeedViewModel extends ViewModel {
    private SocialAppDataBase db;
    private SharedPreferencesRepo sharedPref;
    private Executor executor;

    public PostsFeedViewModel(SocialAppDataBase db, SharedPreferencesRepo sharedPref) {
        this.db = db;
        this.sharedPref = sharedPref;
        this.executor = Executors.newFixedThreadPool(2);;
    }

    LiveData<List<Post>> getAllPosts() {
        return db.getPostDao().getAll();
    }
}
