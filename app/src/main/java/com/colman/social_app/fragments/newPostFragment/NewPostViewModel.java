package com.colman.social_app.fragments.newPostFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.colman.social_app.entities.Post;
import com.colman.social_app.repositories.FirebaseRepo;
import com.colman.social_app.repositories.SharedPreferencesRepo;
import com.colman.social_app.repositories.SocialAppDataBase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NewPostViewModel extends ViewModel {
    private SocialAppDataBase dataBase;
    private SharedPreferencesRepo sharedPref;
    private FirebaseRepo firebaseRepo;
    private Executor executor;


    public NewPostViewModel(SocialAppDataBase dataBase, SharedPreferencesRepo sharedPref, FirebaseRepo firebaseRepo) {
        this.dataBase = dataBase;
        this.sharedPref = sharedPref;
        this.firebaseRepo = firebaseRepo;
        this.executor = Executors.newFixedThreadPool(2);
    }

    public void savePost(Post post) {
        executor.execute(() -> {
            this.dataBase.getPostDao().upsert(post);
            firebaseRepo.savePost(post);
        });
    }

    public LiveData<Post> getPostByID(String id) {
        return this.dataBase.getPostDao().getByID(id);
    }

    public String getCurrUserEmail() {
        return sharedPref.getCurrUserEmail();
    }
}