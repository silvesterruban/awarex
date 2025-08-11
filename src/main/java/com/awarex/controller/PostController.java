package com.awarex.controller;

import com.awarex.model.Post;
import com.awarex.model.User;
import com.awarex.model.request.WritePostRequest;
import com.awarex.model.response.WritePostResponse;
import com.awarex.service.GoRestService;
import com.awarex.util.ExecutionTimer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class PostController {
    private static final Logger logger = Logger.getLogger(PostController.class.getName());

    private final GoRestService goRestService;

    @Autowired
    public PostController(GoRestService goRestService) {
        this.goRestService = goRestService;
    }

    @PostMapping("/writeAPost")
    public ResponseEntity<WritePostResponse> writePost(@Valid @RequestBody WritePostRequest request) {
        return ExecutionTimer.measure("writePost", () -> {
            try {
                // Check if user exists by email
                User user = goRestService.findUserByEmail(request.getUserEmail());
                
                if (user == null) {
                    // User doesn't exist, create a new one
                    user = goRestService.createUser(
                        request.getUserName(),
                        request.getUserEmail(),
                        "male" // Default gender, can be made configurable
                    );
                }
                
                // Create post for the user
                Post post = goRestService.createPost(
                    user.getId(),
                    request.getPostTitle(),
                    request.getPostBody()
                );
                
                // Build response
                WritePostResponse response = new WritePostResponse(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    post.getId(),
                    post.getTitle(),
                    post.getBody()
                );
                
                return ResponseEntity.ok(response);
                
            } catch (Exception e) {
                logger.severe("Error in writePost: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new WritePostResponse("Error processing request: " + e.getMessage()));
            }
        });
    }
    
    @GetMapping("/getUserPostDetails")
    public CompletableFuture<ResponseEntity<List<Post>>> getUserPostDetails() {
        return goRestService.getAllPostsAsync()
            .thenApply(ResponseEntity::ok)
            .exceptionally(e -> {
                logger.severe("Error getting user posts: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            });
    }
}