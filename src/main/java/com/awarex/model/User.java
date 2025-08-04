package com.awarex.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty("user_id")
    private Long userId;
    
    @JsonProperty("name")
    private String userName;
    
    @JsonProperty("email")
    private String userEmail;
    
    @JsonProperty("gender")
    private String userGender;
    
    @JsonProperty("status")
    private String userStatus;
    
    // Constructors
    public User() {}
    
    public User(Long userId, String userName, String userEmail, String userGender, String userStatus) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userGender = userGender;
        this.userStatus = userStatus;
    }
    
    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    
    public String getUserGender() { return userGender; }
    public void setUserGender(String userGender) { this.userGender = userGender; }
    
    public String getUserStatus() { return userStatus; }
    public void setUserStatus(String userStatus) { this.userStatus = userStatus; }
}