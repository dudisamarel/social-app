package com.colman.social_app.intro;

import java.io.Serializable;

public class Data implements Serializable {
    String text;
    Integer img;

    public Data(String text, Integer img) {
        this.text = text;
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public Integer getImg() {
        return img;
    }
}