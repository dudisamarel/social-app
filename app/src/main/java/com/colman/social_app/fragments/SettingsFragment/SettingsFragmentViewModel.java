package com.colman.social_app.fragments.SettingsFragment;

import androidx.lifecycle.ViewModel;

import com.colman.social_app.repositories.SharedPreferencesRepo;

public class SettingsFragmentViewModel extends ViewModel {
    private SharedPreferencesRepo sharedPref;

    public SettingsFragmentViewModel(SharedPreferencesRepo sharedPref) {
        this.sharedPref = sharedPref;
    }

    public boolean getEnableShaking() {
        return sharedPref.getEnableShaking();
    }

    public void setEnableShaking(boolean enableShaking) {
        sharedPref.setEnableShaking(enableShaking);
    }
}
