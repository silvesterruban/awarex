package com.awarex.controller;

import com.awarex.dto.*;
import com.awarex.model.Post;
import com.awarex.model.User;
import com.awarex.service.GoRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PostController {
    
    @Autowired
    private GoRestService goRestService;
    
    @PostMapping("/writeAPost")
    public ResponseEntity<?> writeAPost(@RequestBody WritePostRequest request) {
        try {
            // Check if user exists by email
            User existingUser = goRestService.findUserByEmail(request.getEmail());
            User user;
            
            if (existingUser != null) {
                user = existingUser;
            } else {
                // Create new user
                user = goRestService.createUser(request.getName(), request.getEmail(), request.getGender());
            }
            
            // Create post for the user
            Post post = goRestService.createPost(user.getUserId(), request.getTitle(), request.getBody());
            
            // Create response without ID fields
            WritePostResponse response = new WritePostResponse(
                user.getUserName(),
                user.getUserGender(),
                user.getUserEmail(),
                user.getUserStatus(),
                post.getPostTitle(),
                post.getPostBody()
            );
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            List<String> errors = Arrays.asList(e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse("Error in writeAPost API", errors);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    @GetMapping("/getUserPostDetails")
    public ResponseEntity<?> getUserPostDetails() {
        try {
            // Execute parallel requests
            CompletableFuture<List<User>> usersFuture = goRestService.getAllUsersAsync();
            CompletableFuture<List<Post>> postsFuture = goRestService.getAllPostsAsync();
            
            // Wait for both requests to complete
            List<User> users = usersFuture.get();
            List<Post> posts = postsFuture.get();
            
            // Create user ID set for quick lookup
            Set<Long> userIds = users.stream()
                    .map(User::getUserId)
                    .collect(Collectors.toSet());
            
            // Group posts by user ID and sort by post ID
            Map<Long, List<Post>> userPosts = posts.stream()
                    .filter(post -> userIds.contains(post.getUserId()))
                    .collect(Collectors.groupingBy(
                            Post::getUserId,
                            Collectors.collectingAndThen(
                                    Collectors.toList(),
                                    list -> list.stream()
                                            .sorted(Comparator.comparing(Post::getId))
                                            .collect(Collectors.toList())
                            )
                    ));
            
            // Calculate statistics
            int usersWithPosts = userPosts.size();
            int usersWithoutPosts = users.size() - usersWithPosts;
            int postsWithoutUsers = (int) posts.stream()
                    .filter(post -> !userIds.contains(post.getUserId()))
                    .count();
            
            UserPostDetailsResponse response = new UserPostDetailsResponse(
                    userPosts,
                    usersWithoutPosts,
                    postsWithoutUsers,
                    usersWithPosts
            );
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            List<String> errors = Arrays.asList(e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse("Error in getUserPostDetails API", errors);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}