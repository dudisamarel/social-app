package com.colman.social_app;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.colman.social_app.fragments.newPostFragment.NewPostViewModel;
import com.colman.social_app.repositories.SocialAppDataBase;

import java.security.InvalidParameterException;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private SocialAppDataBase db;

    public ViewModelFactory(SocialAppDataBase db) {
        this.db = db;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(NewPostViewModel.class)) {
            return (T) new NewPostViewModel(db);
        }

        throw new InvalidParameterException();
    }
}
