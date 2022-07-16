package com.colman.social_app;

import android.app.Application;

import androidx.room.Room;

import com.colman.social_app.repositories.FirebaseRepo;
import com.colman.social_app.repositories.SharedPreferencesRepo;
import com.colman.social_app.repositories.SocialAppDataBase;
import com.colman.social_app.services.utils.Converters;

public class SocialApplication extends Application {
    private SocialAppDataBase db;
    private ViewModelFactory viewModelFactory;
    private SharedPreferencesRepo sharedPref;
    private FirebaseRepo firebaseRepo;
    @Override
    public void onCreate() {
        super.onCreate();

        db = Room.databaseBuilder(
                getApplicationContext(),
                SocialAppDataBase.class,
                "social-db"
        ).build();

        firebaseRepo = new FirebaseRepo();

        sharedPref = new SharedPreferencesRepo(this);
        viewModelFactory = new ViewModelFactory(db, sharedPref, firebaseRepo);
    }

    public SocialAppDataBase getDb() {
        return db;
    }

    public ViewModelFactory getViewModelFactory() {
        return viewModelFactory;
    }

    public FirebaseRepo getFirebaseRepo() {return firebaseRepo;}
}
