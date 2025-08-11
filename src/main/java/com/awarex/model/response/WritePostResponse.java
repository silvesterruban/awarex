package com.awarex.model.response;

public class WritePostResponse {
    private final Long userId;
    private final String userName;
    private final String userEmail;
    private final Long postId;
    private final String postTitle;
    private final String postBody;
    private final String error;

    public WritePostResponse(Long userId, String userName, String userEmail, 
                           Long postId, String postTitle, String postBody) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.postId = postId;
        this.postTitle = postTitle;
        this.postBody = postBody;
        this.error = null;
    }

    public WritePostResponse(String error) {
        this.userId = null;
        this.userName = null;
        this.userEmail = null;
        this.postId = null;
        this.postTitle = null;
        this.postBody = error;
        this.error = error;
    }

    // Getters
    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Long getPostId() {
        return postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostBody() {
        return postBody;
    }

    public String getError() {
        return error;
    }
}
