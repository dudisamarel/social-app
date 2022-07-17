package com.colman.social_app.repositories.daos;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.colman.social_app.entities.Post;

import java.util.List;

@Dao
public interface PostDao{

    @Insert(onConflict = REPLACE)
    void upsert(Post post); //update and insert

    @Insert(onConflict = REPLACE)
    void insert(Post[] posts);

    @Query("SELECT * FROM posts WHERE isDeleted = 0 ORDER BY edited DESC")
    LiveData<List<Post>> getAll();

    @Query("SELECT * FROM posts WHERE id = :id")
    LiveData<Post> getByID(String id);

    @Delete
    void delete(Post post);

    @Query("UPDATE posts SET isDeleted = 1 WHERE id = :id")
    void delete(String id);



}
