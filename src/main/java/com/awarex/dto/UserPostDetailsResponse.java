package com.awarex.dto;

import com.awarex.model.Post;
import java.util.List;
import java.util.Map;

public class UserPostDetailsResponse {
    private Map<Long, List<Post>> userPosts;
    private int usersWithoutPosts;
    private int postsWithoutUsers;
    private int usersWithPosts;
    
    // Constructors
    public UserPostDetailsResponse() {}
    
    public UserPostDetailsResponse(Map<Long, List<Post>> userPosts, int usersWithoutPosts, 
                                 int postsWithoutUsers, int usersWithPosts) {
        this.userPosts = userPosts;
        this.usersWithoutPosts = usersWithoutPosts;
        this.postsWithoutUsers = postsWithoutUsers;
        this.usersWithPosts = usersWithPosts;
    }
    
    // Getters and Setters
    public Map<Long, List<Post>> getUserPosts() { return userPosts; }
    public void setUserPosts(Map<Long, List<Post>> userPosts) { this.userPosts = userPosts; }
    
    public int getUsersWithoutPosts() { return usersWithoutPosts; }
    public void setUsersWithoutPosts(int usersWithoutPosts) { this.usersWithoutPosts = usersWithoutPosts; }
    
    public int getPostsWithoutUsers() { return postsWithoutUsers; }
    public void setPostsWithoutUsers(int postsWithoutUsers) { this.postsWithoutUsers = postsWithoutUsers; }
    
    public int getUsersWithPosts() { return usersWithPosts; }
    public void setUsersWithPosts(int usersWithPosts) { this.usersWithPosts = usersWithPosts; }
}