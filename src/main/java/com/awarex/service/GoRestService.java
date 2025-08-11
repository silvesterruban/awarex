package com.awarex.service;

import com.awarex.model.Post;
import com.awarex.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@Service
public class GoRestService {
    private static final String BASE_URL = "https://gorest.co.in/public/v2";
    private static final String TOKEN = "1f9ac8acaa3e11d3aac29afda5944bf5d4ee67dcd5dd4e8ccbd2d94f0e289eb8";
    private static final Logger LOG = Logger.getLogger(GoRestService.class.getName());

    private final Client client;
    private final ObjectMapper objectMapper;
    
    public GoRestService(Client client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }
    
    public User createUser(String name, String email, String gender) {
        long startTime = System.currentTimeMillis();
        try {
            String userJson = String.format(
                "{\"name\":\"%s\",\"email\":\"%s\",\"gender\":\"%s\",\"status\":\"active\"}",
                name, email, gender
            );
            
            Response response = client.target(BASE_URL + "/users")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + TOKEN)
                .post(Entity.json(userJson));
                
            String responseBody = response.readEntity(String.class);
            
            if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
                String errorMsg = String.format("Failed to create user. Status: %d, Response: %s",
                    response.getStatus(), responseBody);
                LOG.severe(errorMsg);
                throw new RuntimeException(errorMsg);
            }
            
            return objectMapper.readValue(responseBody, User.class);
            
        } catch (Exception e) {
            LOG.severe("Error creating user: " + e.getMessage());
            throw new RuntimeException("Error processing user creation: " + e.getMessage(), e);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            LOG.info(String.format("createUser API call took %d ms", duration));
        }
    }

    public Post createPost(Long userId, String title, String body) {
        long startTime = System.currentTimeMillis();
        try {
            String postJson = String.format(
                "{\"user_id\":%d,\"title\":\"%s\",\"body\":\"%s\"}",
                userId, title, body
            );
            
            Response response = client.target(BASE_URL + "/posts")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + TOKEN)
                .post(Entity.json(postJson));
                
            String responseBody = response.readEntity(String.class);
            
            if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
                String errorMsg = String.format("Failed to create post. Status: %d, Response: %s",
                    response.getStatus(), responseBody);
                LOG.severe(errorMsg);
                throw new RuntimeException(errorMsg);
            }
            
            return objectMapper.readValue(responseBody, Post.class);
            
        } catch (Exception e) {
            LOG.severe("Error creating post: " + e.getMessage());
            throw new RuntimeException("Error processing post creation: " + e.getMessage(), e);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            LOG.info(String.format("createPost API call took %d ms", duration));
        }
    }

    public User findUserByEmail(String email) {
        long startTime = System.currentTimeMillis();
        Response response = null;
        try {
            // Create the target URL with query parameter
            WebTarget target = client.target(BASE_URL + "/users")
                .queryParam("email", email);
                
            // Build and execute the request
            response = target.request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + TOKEN)
                .get();
                
            // Check if we got a successful response
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                LOG.warning("Failed to find user by email: " + email + ", Status: " + response.getStatus());
                return null;
            }
            
            // Read and parse the response
            String responseBody = response.readEntity(String.class);
            if (responseBody == null || responseBody.trim().isEmpty()) {
                LOG.warning("Empty response received when finding user by email: " + email);
                return null;
            }
            
            // Parse the JSON array response - handle both array and single object responses
            if (responseBody.trim().startsWith("[")) {
                // Handle array response
                List<User> users = objectMapper.readValue(responseBody, 
                    objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
                return users.isEmpty() ? null : users.get(0);
            } else {
                // Handle single object response (though tests expect array)
                return objectMapper.readValue(responseBody, User.class);
            }
            
        } catch (Exception e) {
            LOG.severe("Error finding user by email " + email + ": " + e.getMessage());
            throw new RuntimeException("Error processing user search: " + e.getMessage(), e);
        } finally {
            // Ensure the response is closed
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    LOG.warning("Error closing response: " + e.getMessage());
                }
            }
            long duration = System.currentTimeMillis() - startTime;
            LOG.info(String.format("findUserByEmail for %s took %d ms", email, duration));
        }
    }
    
    public List<User> getAllUsers() {
        long startTime = System.currentTimeMillis();
        try {
            Response response = client.target(BASE_URL + "/users")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + TOKEN)
                .get();
                
            String responseBody = response.readEntity(String.class);
            
            if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
                String errorMsg = String.format("Failed to get users. Status: %d, Response: %s",
                    response.getStatus(), responseBody);
                LOG.severe(errorMsg);
                throw new RuntimeException(errorMsg);
            }
            
            return objectMapper.readValue(responseBody, new TypeReference<List<User>>() {});
            
        } catch (Exception e) {
            LOG.severe("Error getting users: " + e.getMessage());
            throw new RuntimeException("Error fetching users: " + e.getMessage(), e);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            LOG.info(String.format("getAllUsers API call took %d ms", duration));
        }
    }
    
    public List<Post> getAllPosts() {
        long startTime = System.currentTimeMillis();
        try {
            Response response = client.target(BASE_URL + "/posts")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + TOKEN)
                .get();
                
            String responseBody = response.readEntity(String.class);
            
            if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
                String errorMsg = String.format("Failed to get posts. Status: %d, Response: %s",
                    response.getStatus(), responseBody);
                LOG.severe(errorMsg);
                throw new RuntimeException(errorMsg);
            }
            
            return objectMapper.readValue(responseBody, new TypeReference<List<Post>>() {});
            
        } catch (Exception e) {
            LOG.severe("Error getting posts: " + e.getMessage());
            throw new RuntimeException("Error fetching posts: " + e.getMessage(), e);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            LOG.info(String.format("getAllPosts API call took %d ms", duration));
        }
    }
    
    public CompletableFuture<List<User>> getAllUsersAsync() {
        return CompletableFuture.supplyAsync(this::getAllUsers);
    }
    
    public CompletableFuture<List<Post>> getAllPostsAsync() {
        return CompletableFuture.supplyAsync(this::getAllPosts);
    }
}