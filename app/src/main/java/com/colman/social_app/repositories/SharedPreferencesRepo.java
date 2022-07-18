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

    public void setEnableShaking(boolean enableShaking) {
        sharedPreferences.edit().putBoolean("enable_shaking", enableShaking).apply();
    }

    public boolean getEnableShaking() {
        return sharedPreferences.getBoolean("enable_shaking", true);
    }

    public boolean getViewCurrentUserPosts() {
        return sharedPreferences.getBoolean("view_user_posts", false);
    }

    public void setViewCurrentUserPosts(boolean viewCurrentUserPosts) {
        sharedPreferences.edit().putBoolean("view_user_posts", viewCurrentUserPosts).apply();
    }
}
