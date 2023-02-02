package com.example.schat;


import java.io.Serializable;

public class User implements Serializable {

    private String email;
    private String userName;
    private String profilePic;
    private String status;

    private String userId;


    public User() {
    }

    public User(String email, String userName, String profilePic, String status) {
        this.email = email;
        this.userName = userName;
        this.profilePic = profilePic;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getStatus() {
        return status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
