package com.awarex.dto;

public class WritePostRequest {
    private String name;
    private String gender;
    private String email;
    private String title;
    private String body;
    
    // Constructors
    public WritePostRequest() {}
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
}
