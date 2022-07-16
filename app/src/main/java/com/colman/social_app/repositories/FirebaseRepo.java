package com.colman.social_app.repositories;

import com.colman.social_app.entities.Post;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseRepo {
    FirebaseFirestore fireBaseDB;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    public FirebaseRepo() {
        fireBaseDB = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        fireBaseDB.setFirestoreSettings(settings);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
    }

    public void savePost(Post post) {
        fireBaseDB.collection("Posts").add(Post.toMap(post));
    }

    public Task<QuerySnapshot> getAllPosts() {
        return fireBaseDB.collection("Posts").get();
    }
}
