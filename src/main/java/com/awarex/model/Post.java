package com.awarex.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Post {
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("user_id")
    private Long userId;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("body")
    private String body;

    // Constructors
    public Post() {}
    
    public Post(Long id, Long userId, String title, String body) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}