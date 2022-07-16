package com.colman.social_app.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

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
        this.created = created;
        this.edited = edited;
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
}