package com.awarex.service;

import com.awarex.model.User;
import com.awarex.model.Post;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class GoRestService {
    private static final String BASE_URL = "https://gorest.co.in/public/v2";
    private static final String TOKEN = "Bearer YOUR_TOKEN_HERE"; // Replace with actual token
    
    private final Client client;
    private final ObjectMapper objectMapper;
    
    public GoRestService() {
        this.client = ClientBuilder.newClient();
        this.objectMapper = new ObjectMapper();
    }
    
    public User createUser(String name, String email, String gender) throws Exception {
        Map<String, Object> userRequest = new HashMap<>();
        userRequest.put("name", name);
        userRequest.put("email", email);
        userRequest.put("gender", gender);
        userRequest.put("status", "active");
        
        Response response = client.target(BASE_URL + "/users")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", TOKEN)
                .post(Entity.json(userRequest));
                
        if (response.getStatus() >= 400) {
            throw new Exception("Error creating user: " + response.readEntity(String.class));
        }
        
        String responseJson = response.readEntity(String.class);
        return objectMapper.readValue(responseJson, User.class);
    }
    
    public Post createPost(Long userId, String title, String body) throws Exception {
        Map<String, Object> postRequest = new HashMap<>();
        postRequest.put("user_id", userId);
        postRequest.put("title", title);
        postRequest.put("body", body);
        
        Response response = client.target(BASE_URL + "/posts")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", TOKEN)
                .post(Entity.json(postRequest));
                
        if (response.getStatus() >= 400) {
            throw new Exception("Error creating post: " + response.readEntity(String.class));
        }
        
        String responseJson = response.readEntity(String.class);
        return objectMapper.readValue(responseJson, Post.class);
    }
    
    public User findUserByEmail(String email) throws Exception {
        Response response = client.target(BASE_URL + "/users")
                .queryParam("email", email)
                .request(MediaType.APPLICATION_JSON)
                .get();
                
        if (response.getStatus() >= 400) {
            throw new Exception("Error fetching user: " + response.readEntity(String.class));
        }
        
        String responseJson = response.readEntity(String.class);
        List<User> users = objectMapper.readValue(responseJson, new TypeReference<List<User>>() {});
        
        return users.isEmpty() ? null : users.get(0);
    }
    
    public CompletableFuture<List<User>> getAllUsersAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Response response = client.target(BASE_URL + "/users")
                        .request(MediaType.APPLICATION_JSON)
                        .get();
                        
                if (response.getStatus() >= 400) {
                    throw new RuntimeException("Error fetching users: " + response.readEntity(String.class));
                }
                
                String responseJson = response.readEntity(String.class);
                return objectMapper.readValue(responseJson, new TypeReference<List<User>>() {});
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
    
    public CompletableFuture<List<Post>> getAllPostsAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Response response = client.target(BASE_URL + "/posts")
                        .request(MediaType.APPLICATION_JSON)
                        .get();
                        
                if (response.getStatus() >= 400) {
                    throw new RuntimeException("Error fetching posts: " + response.readEntity(String.class));
                }
                
                String responseJson = response.readEntity(String.class);
                return objectMapper.readValue(responseJson, new TypeReference<List<Post>>() {});
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}