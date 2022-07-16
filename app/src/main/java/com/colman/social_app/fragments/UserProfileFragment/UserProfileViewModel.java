package com.colman.social_app.fragments.UserProfileFragment;

import android.content.SharedPreferences;
import android.net.Uri;

import androidx.lifecycle.ViewModel;

import com.colman.social_app.repositories.FirebaseRepo;
import com.colman.social_app.repositories.SharedPreferencesRepo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseUser;

public class UserProfileViewModel extends ViewModel {
    private final FirebaseRepo firebaseRepo;
    private final SharedPreferencesRepo sharedPref;

    public UserProfileViewModel(FirebaseRepo firebaseRepo, SharedPreferencesRepo sharedPref) {
        this.firebaseRepo = firebaseRepo;
        this.sharedPref = sharedPref;
    }

    public void userReAuth(String email, String password, OnCompleteListener<Void> listener) {
        firebaseRepo.userReAuth(email, password, listener);
    }

    public void updateName(String newName, OnCompleteListener<Void> listener) {
        firebaseRepo.updateUserName(newName, listener);
    }

    public void updatePassword(String newPassword, OnCompleteListener<Void> listener) {
        firebaseRepo.updateUserPassword(newPassword, listener);
    }

    public void uploadImage(String imageName, Uri imageUri, OnCompleteListener<Uri> listener) {
        firebaseRepo.uploadImageToStorage(imageName, imageUri, listener);
    }

    public void setNewImage(String imageUri, OnCompleteListener<Void> listener) {
        firebaseRepo.updateUserProfileImage(imageUri, listener);
    }

    public void signOut() {
        firebaseRepo.signUserOut();
        sharedPref.clear();
    }

    public FirebaseUser getUser() {
        return firebaseRepo.getUser();
    }
}
