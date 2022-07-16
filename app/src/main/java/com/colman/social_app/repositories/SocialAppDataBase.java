package com.colman.social_app.repositories;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.colman.social_app.entities.Post;
import com.colman.social_app.repositories.daos.PostDao;
import com.colman.social_app.services.utils.Converters;

@Database(version = 1, entities = {
        Post.class
})
@TypeConverters({Converters.class})
public abstract class SocialAppDataBase extends RoomDatabase {

     public abstract PostDao getPostDao();
}
