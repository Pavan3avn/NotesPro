package com.pavan.notespro.models;

import com.google.firebase.Timestamp;

public class Notemodel {

    String title;
    String content;
    Timestamp timestamp;

    public Notemodel(){

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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
