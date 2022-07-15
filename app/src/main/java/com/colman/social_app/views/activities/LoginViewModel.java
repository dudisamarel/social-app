package com.colman.social_app.views.activities;

import androidx.lifecycle.ViewModel;

import com.colman.social_app.repositories.SharedPreferencesRepo;

public class LoginViewModel extends ViewModel {
    private SharedPreferencesRepo sharedPref;

    public LoginViewModel(SharedPreferencesRepo sharedPref) {
        this.sharedPref = sharedPref;
    }

    public void setCurrUserEmail(String email) {
        sharedPref.setCurrUser(email);
    }
}
