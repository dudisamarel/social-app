package com.colman.social_app.fragments.newPostFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.colman.social_app.SocialApplication;
import com.colman.social_app.entities.Post;
import com.colman.social_app.repositories.SocialAppDataBase;

public class NewPostViewModel extends ViewModel {
    private SocialAppDataBase dataBase;

    public NewPostViewModel() {
    }

    public NewPostViewModel(SocialAppDataBase dataBase) {
        this.dataBase = dataBase;
    }

    public void savePost(Post post) {
        this.dataBase.getPostDao().upsert(post);
    }

    public LiveData<Post> getPostByID(String id) {
        return this.dataBase.getPostDao().getByID(id);
    }
}
