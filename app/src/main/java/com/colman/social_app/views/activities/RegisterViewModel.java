package com.colman.social_app.views.activities;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

import com.colman.social_app.repositories.FirebaseRepo;
import com.colman.social_app.repositories.SharedPreferencesRepo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseUser;

public class RegisterViewModel extends ViewModel {
    private final FirebaseRepo firebaseRepo;

    public RegisterViewModel(FirebaseRepo firebaseRepo) {
        this.firebaseRepo = firebaseRepo;
    }

    public void uploadImage(String imageName, Uri imageUri, OnCompleteListener<Uri> listener) {
        firebaseRepo.uploadImageToStorage(imageName, imageUri, listener);
    }
}
