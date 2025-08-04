package com.awarex.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Post {
    private Long id;
    
    @JsonProperty("user_id")
    private Long userId;
    
    @JsonProperty("title")
    private String postTitle;
    
    @JsonProperty("body")
    private String postBody;
    
    // Constructors
    public Post() {}
    
    public Post(Long id, Long userId, String postTitle, String postBody) {
        this.id = id;
        this.userId = userId;
        this.postTitle = postTitle;
        this.postBody = postBody;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public String getPostTitle() { return postTitle; }
    public void setPostTitle(String postTitle) { this.postTitle = postTitle; }
    
    public String getPostBody() { return postBody; }
    public void setPostBody(String postBody) { this.postBody = postBody; }
}