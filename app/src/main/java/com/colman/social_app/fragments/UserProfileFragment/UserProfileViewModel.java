package com.colman.social_app.fragments.UserProfileFragment;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

import com.colman.social_app.repositories.FirebaseRepo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseUser;

public class UserProfileViewModel extends ViewModel {
    private final FirebaseRepo firebaseRepo;


    public UserProfileViewModel(FirebaseRepo firebaseRepo) {
        this.firebaseRepo = firebaseRepo;
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

    public void setNewImage() {

    }

    public void signOut(){
        firebaseRepo.signUserOut();
    }

    public FirebaseUser getUser() {
        return firebaseRepo.getUser();
    }
}
