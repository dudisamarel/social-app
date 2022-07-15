package com.colman.social_app.repositories;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.colman.social_app.entities.Post;
import com.colman.social_app.repositories.daos.PostDao;

@Database(version = 1, entities = {
        Post.class
})
public abstract class SocialAppDataBase extends RoomDatabase {

     public abstract PostDao getPostDao();
}
