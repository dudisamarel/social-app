package com.colman.social_app.repositories;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.colman.social_app.entities.Post;
import com.colman.social_app.views.activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class FirebaseRepo {
    FirebaseFirestore fireBaseDB;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseAuth mAuth;
    FirebaseUser user;

    public FirebaseRepo() {
        fireBaseDB = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        fireBaseDB.setFirestoreSettings(settings);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
    }

    public void signUserOut() {
        mAuth.signOut();
    }

    public void savePost(Post post) {
        fireBaseDB.collection("Posts").add(Post.toMap(post));
    }

    public void login(String email, String password, OnCompleteListener<AuthResult> listener) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }

    public FirebaseUser getUser() {
        return mAuth.getCurrentUser();
    }

    public void userReAuth(String email, String password, OnCompleteListener<Void> listener) {
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null)
            user.reauthenticate(credential).addOnCompleteListener(listener);
    }

    public void updateUserName(String newName, OnCompleteListener<Void> listener) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null)
            user.updateProfile(profileUpdates).addOnCompleteListener(listener);
    }

    public Task<QuerySnapshot> getAllPosts() {
        return fireBaseDB.collection("Posts").get();
    }

    public void uploadImageToStorage(String imageName, Uri imageUri, OnCompleteListener<Uri> listener) {
        if (imageUri != null) {
            StorageReference storageReference = firebaseStorage.getReference("photos");
            final StorageReference imageRef = storageReference.child(imageName);
            UploadTask uploadTask = imageRef.putFile(imageUri);
            uploadTask.continueWithTask(task -> imageRef.getDownloadUrl()).addOnCompleteListener(listener);
        }
    }

    public void updateUserProfileImage(String imageUri, OnCompleteListener<Void> listener) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(Uri.parse(imageUri))
                .build();
        if (user != null)
            user.updateProfile(profileUpdates).addOnCompleteListener(listener);
    }

    public void updateUserPassword(String newPassword, OnCompleteListener<Void> listener) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null)
            user.updatePassword(newPassword).addOnCompleteListener(listener);
    }
}
