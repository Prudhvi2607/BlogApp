package com.praneeth.studio.blogapp.Model;

public class Blog {
    public String title;
    public String userid;
    public String timestamp;
    public String image;

    public Blog() {
    }

    public Blog(String title, String userid, String timestamp, String image) {
        this.title = title;
        this.userid = userid;
        this.timestamp = timestamp;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getimage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
