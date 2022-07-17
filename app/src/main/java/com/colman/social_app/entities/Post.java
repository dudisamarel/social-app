package com.colman.social_app.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "posts")
public class Post {

    @PrimaryKey()
    @NonNull
    String id;
    String title;
    String content;
    String attachmentURI;
    String uploaderEmail;
    Date created;
    Date edited;
    boolean isDeleted;

    public Post() {

    }

    public Post(
            String id,
            String title,
            String content,
            String attachmentURI,
            String uploaderEmail,
            Date created,
            Date edited,
            boolean isDeleted
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.attachmentURI = attachmentURI;
        this.uploaderEmail = uploaderEmail;

        if (created == null) {
            this.created = new Date();
        } else {
            this.created = created;
        }

        if (edited == null) {
            this.edited = new Date();
        } else {
            this.edited = created;
        }

        this.isDeleted = isDeleted;
    }

    public Post(
            String id,
            String title,
            String content,
            String attachmentURI,
            String uploaderEmail
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.attachmentURI = attachmentURI;
        this.uploaderEmail = uploaderEmail;
        Date date = new Date();
        this.created = date;
        this.edited = date;
        this.isDeleted = false;
    }

    public Post(
            String id,
            String title,
            String content,
            String attachmentURI,
            String uploaderEmail,
            Date created
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.attachmentURI = attachmentURI;
        this.uploaderEmail = uploaderEmail;
        Date date = new Date();
        this.created = created;
        this.edited = date;
        this.isDeleted = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachmentURI() {
        return attachmentURI;
    }

    public void setAttachmentURI(String attachmentURI) {
        this.attachmentURI = attachmentURI;
    }

    public String getUploaderEmail() {
        return uploaderEmail;
    }

    public void setUploaderEmail(String uploaderEmail) {
        this.uploaderEmail = uploaderEmail;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getEdited() {
        return edited;
    }

    public void setEdited(Date edited) {
        this.edited = edited;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public static Map<String, Object> toMap(Post post) {
        HashMap<String, Object> postMap = new HashMap<>();
        postMap.put("_id", post.id);
        postMap.put("title", post.title);
        postMap.put("content", post.content);
        postMap.put("attachmentURI", post.attachmentURI);
        postMap.put("uploaderEmail", post.uploaderEmail);
        postMap.put("created", post.created.getTime());
        postMap.put("edited", post.edited.getTime());
        postMap.put("isDeleted", post.isDeleted);

        return postMap;
    }

    public static Post fromMap(DocumentSnapshot map) {
        return new Post(
                (String) map.get("_id"),
                (String) map.get("title"),
                (String) map.get("content"),
                (String) map.get("attachmentURI"),
                (String) map.get("uploaderEmail"),
                new Date((long) map.get("created")),
                new Date((long) map.get("edited")),
                (boolean) map.get("isDeleted")
        );
    }
}