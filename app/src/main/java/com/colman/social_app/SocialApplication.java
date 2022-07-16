package com.colman.social_app;

import android.app.Application;

import androidx.room.Room;

import com.colman.social_app.repositories.SharedPreferencesRepo;
import com.colman.social_app.repositories.SocialAppDataBase;
import com.colman.social_app.services.utils.Converters;

public class SocialApplication extends Application {
    private SocialAppDataBase db;
    private ViewModelFactory viewModelFactory;
    private SharedPreferencesRepo sharedPref;
    private Converters converters;

    @Override
    public void onCreate() {

        converters = new Converters();

        super.onCreate();
        db = Room.databaseBuilder(
                getApplicationContext(),
                SocialAppDataBase.class,
                "social-db"
        ).build();

        sharedPref = new SharedPreferencesRepo(this);
        viewModelFactory = new ViewModelFactory(db, sharedPref);
    }

    public SocialAppDataBase getDb() {
        return db;
    }

    public ViewModelFactory getViewModelFactory() {
        return viewModelFactory;
    }
}
