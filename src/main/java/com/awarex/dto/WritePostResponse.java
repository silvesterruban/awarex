package com.awarex.dto;

public class WritePostResponse {
    private String userName;
    private String userGender;
    private String userEmail;
    private String userStatus;
    private String postTitle;
    private String postBody;
    
    // Constructors
    public WritePostResponse() {}
    
    public WritePostResponse(String userName, String userGender, String userEmail, 
                           String userStatus, String postTitle, String postBody) {
        this.userName = userName;
        this.userGender = userGender;
        this.userEmail = userEmail;
        this.userStatus = userStatus;
        this.postTitle = postTitle;
        this.postBody = postBody;
    }
    
    // Getters and Setters
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public String getUserGender() { return userGender; }
    public void setUserGender(String userGender) { this.userGender = userGender; }
    
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    
    public String getUserStatus() { return userStatus; }
    public void setUserStatus(String userStatus) { this.userStatus = userStatus; }
    
    public String getPostTitle() { return postTitle; }
    public void setPostTitle(String postTitle) { this.postTitle = postTitle; }
    
    public String getPostBody() { return postBody; }
    public void setPostBody(String postBody) { this.postBody = postBody; }
}
