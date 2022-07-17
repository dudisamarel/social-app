package com.colman.social_app.repositories;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesRepo {
    SharedPreferences sharedPreferences;

    public SharedPreferencesRepo(Context context) {
        sharedPreferences = context.getSharedPreferences("SocialApp", Context.MODE_PRIVATE);
    }

    public void setCurrUser(String currUserEmail) {
        sharedPreferences.edit().putString("email", currUserEmail).apply();
    }

    public void clear() {
        sharedPreferences.edit().clear().apply();
    }

    public String getCurrUserEmail() {
        return sharedPreferences.getString("email", "");
    }

    public void setLastSync(long syncTimeStamp) {
        sharedPreferences.edit().putLong("last_sync", syncTimeStamp).apply();
    }

    public long getLastSync() {
        return sharedPreferences.getLong("last_sync", 0);
    }
}
