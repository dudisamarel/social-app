package com.colman.social_app;

import android.app.Application;

import androidx.room.Room;

import com.colman.social_app.repositories.SocialAppDataBase;

public class SocialApplication extends Application {
    private SocialAppDataBase db;
    private ViewModelFactory viewModelFactory;

    @Override
    public void onCreate() {
        super.onCreate();
        db = Room.databaseBuilder(
                getApplicationContext(),
                SocialAppDataBase.class,
                "social-db"
        ).build();

        viewModelFactory = new ViewModelFactory(db);
    }

    public SocialAppDataBase getDb() {
        return db;
    }

    public ViewModelFactory getViewModelFactory() {
        return viewModelFactory;
    }
}
