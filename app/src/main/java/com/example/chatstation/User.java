package com.example.chatstation;

public class User {
    public String username;
    public String phone;
    public String photoUrl;
    public String Bio;
    public String UID;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String phone) {
        this.phone = phone;
    }

    public User(String username,String phone,String photoUrl,String bio,String UID){
        this.username = username;
        this.phone = phone;
        this.photoUrl = photoUrl;
        this.Bio = bio;
        this.UID = UID;
    }

}
