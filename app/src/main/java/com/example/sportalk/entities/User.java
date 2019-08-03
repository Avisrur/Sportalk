package com.example.sportalk.entities;


public class User {

    private String id;
    private String username;
    private String email;
    private String profileImage;
    private boolean news;

    public User(String id, String username, String email, String profileImage, boolean news) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.profileImage = profileImage;
        this.news = news;
    }

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public boolean isNews() {
        return news;
    }


    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNews(boolean news) {
        this.news = news;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}