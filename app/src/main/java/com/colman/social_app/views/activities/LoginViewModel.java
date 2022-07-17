package com.colman.social_app.views.activities;

import androidx.lifecycle.ViewModel;

import com.colman.social_app.repositories.FirebaseRepo;
import com.colman.social_app.repositories.SharedPreferencesRepo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;

public class LoginViewModel extends ViewModel {
    private final SharedPreferencesRepo sharedPref;
    private final FirebaseRepo firebaseRepo;

    public LoginViewModel(SharedPreferencesRepo sharedPref, FirebaseRepo firebaseRepo) {
        this.sharedPref = sharedPref;
        this.firebaseRepo = firebaseRepo;
    }

    public void login(String email, String password, OnCompleteListener<AuthResult> listener) {
        firebaseRepo.login(email, password, listener);
    }

    public void saveUser(String email) {
        sharedPref.setCurrUser(email);
    }
}
