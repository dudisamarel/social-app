package com.colman.social_app.fragments.postDetailFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.colman.social_app.entities.Post;
import com.colman.social_app.repositories.FirebaseRepo;
import com.colman.social_app.repositories.SharedPreferencesRepo;
import com.colman.social_app.repositories.SocialAppDataBase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PostDetailsViewModel extends ViewModel {
    private SocialAppDataBase dataBase;
    private Executor executor;

    public PostDetailsViewModel(SocialAppDataBase dataBase) {
        this.dataBase = dataBase;
        this.executor = Executors.newFixedThreadPool(2);
    }

    public LiveData<Post> getPostByID(String id) {
        return this.dataBase.getPostDao().getByID(id);
    }

}
