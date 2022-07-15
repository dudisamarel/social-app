package com.colman.social_app.entities;

public class Post {
    String title;
    String content;
    String attachmentURI;
    String uploaderEmail;

    public Post() {

    }

    public Post(String title, String content, String attachmentURI, String uploaderEmail) {
        this.title = title;
        this.content = content;
        this.attachmentURI = attachmentURI;
        this.uploaderEmail = uploaderEmail;
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
}
