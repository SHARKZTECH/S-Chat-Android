package com.example.schat;

public class User {

    private String email;
    private String userName;
    private String profilePic;
    private String status;

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
}
