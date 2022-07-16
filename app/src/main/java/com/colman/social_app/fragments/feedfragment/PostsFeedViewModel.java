package com.colman.social_app.fragments.feedfragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.colman.social_app.entities.Post;
import com.colman.social_app.repositories.FirebaseRepo;
import com.colman.social_app.repositories.SharedPreferencesRepo;
import com.colman.social_app.repositories.SocialAppDataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PostsFeedViewModel extends ViewModel {
    private SocialAppDataBase db;
    private SharedPreferencesRepo sharedPref;
    private FirebaseRepo firebaseRepo;
    private Executor executor;

    public PostsFeedViewModel(SocialAppDataBase db, SharedPreferencesRepo sharedPref, FirebaseRepo firebaseRepo) {
        this.db = db;
        this.sharedPref = sharedPref;
        this.firebaseRepo = firebaseRepo;
        this.executor = Executors.newFixedThreadPool(2);
    }

    public LiveData<List<Post>> getAllPosts() {
        return db.getPostDao().getAll();
    }

    public void refreshFromRemote() {
        firebaseRepo.getAllPostFromLastSync()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<Post> retrievedPosts = new ArrayList<Post>();
                        for (QueryDocumentSnapshot doc:
                             task.getResult()) {
                            retrievedPosts.add(Post.fromMap(doc));
                        }

                        executor.execute(() -> {
                            db.getPostDao().insert(retrievedPosts.toArray(new Post[0]));
                        });

                    }
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        this.db = null;
        this.sharedPref = null;
        this.executor = null;
    }
}
