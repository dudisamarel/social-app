package com.colman.social_app;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.colman.social_app.fragments.feedfragment.PostsFeedViewModel;
import com.colman.social_app.fragments.newPostFragment.NewPostViewModel;
import com.colman.social_app.repositories.FirebaseRepo;
import com.colman.social_app.repositories.SharedPreferencesRepo;
import com.colman.social_app.repositories.SocialAppDataBase;
import com.colman.social_app.views.activities.Login;
import com.colman.social_app.views.activities.LoginViewModel;

import java.security.InvalidParameterException;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private SocialAppDataBase db;
    private FirebaseRepo firebaseRepo;
    private SharedPreferencesRepo sharedPref;

    public ViewModelFactory(SocialAppDataBase db, SharedPreferencesRepo sharedPref, FirebaseRepo firebaseRepo) {
        this.db = db;
        this.firebaseRepo = firebaseRepo;
        this.sharedPref = sharedPref;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(NewPostViewModel.class)) {
            return (T) new NewPostViewModel(db, sharedPref, firebaseRepo);
        } else if (modelClass.equals(LoginViewModel.class)) {
            return (T) new LoginViewModel(sharedPref);
        } else if (modelClass.equals(PostsFeedViewModel.class)) {
            return (T) new PostsFeedViewModel(db, sharedPref, firebaseRepo);
        }
        throw new InvalidParameterException();
    }
}
