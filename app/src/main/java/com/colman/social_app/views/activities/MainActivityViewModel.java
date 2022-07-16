package com.colman.social_app.views.activities;

import androidx.lifecycle.ViewModel;

import com.colman.social_app.repositories.FirebaseRepo;
import com.colman.social_app.repositories.SharedPreferencesRepo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;

public class MainActivityViewModel extends ViewModel {
    private final FirebaseRepo firebaseRepo;

    public MainActivityViewModel(FirebaseRepo firebaseRepo) {
        this.firebaseRepo = firebaseRepo;
    }

    public Boolean isLoggedIn() {
        return firebaseRepo.getUser() != null;
    }
}