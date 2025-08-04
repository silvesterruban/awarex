// pom.xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.awarex</groupId>
    <artifactId>backend-assignment</artifactId>
    <version>1.0.0</version>
    <packaging>war</packaging>
    
    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <spring.version>5.3.21</spring.version>
        <jersey.version>2.35</jersey.version>
    </properties>
    
    <dependencies>
        <!-- Spring Framework -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
            <version>${spring.version}</version>
        </dependency>
        
        <!-- Jersey Client -->
        <dependency>
            <groupId>org.glassfish.jersey.core</groupId>
            <artifactId>jersey-client</artifactId>
            <version>${jersey.version}</version>
        </dependency>
        <dependency>
            <groupId>org.glassfish.jersey.inject</groupId>
            <artifactId>jersey-hk2</artifactId>
            <version>${jersey.version}</version>
        </dependency>
        
        <!-- JSON Processing -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.13.3</version>
        </dependency>
        
        <!-- Servlet API -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
            <scope>provided</scope>
        </dependency>
        
        <!-- Logging -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.36</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.2.11</version>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-war-plugin</artifactId>
                <version>3.2.3</version>
            </plugin>
        </plugins>
    </build>
</project>

// src/main/java/com/awarex/model/User.java
package com.awarex.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty("user_id")
    private Long userId;
    
    @JsonProperty("name")
    private String userName;
    
    @JsonProperty("email")
    private String userEmail;
    
    @JsonProperty("gender")
    private String userGender;
    
    @JsonProperty("status")
    private String userStatus;
    
    // Constructors
    public User() {}
    
    public User(Long userId, String userName, String userEmail, String userGender, String userStatus) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userGender = userGender;
        this.userStatus = userStatus;
    }
    
    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    
    public String getUserGender() { return userGender; }
    public void setUserGender(String userGender) { this.userGender = userGender; }
    
    public String getUserStatus() { return userStatus; }
    public void setUserStatus(String userStatus) { this.userStatus = userStatus; }
}

// src/main/java/com/awarex/model/Post.java
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

// src/main/java/com/awarex/dto/WritePostRequest.java
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

// src/main/java/com/awarex/dto/WritePostResponse.java
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

// src/main/java/com/awarex/dto/UserPostDetailsResponse.java
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

// src/main/java/com/awarex/dto/ErrorResponse.java
package com.awarex.dto;

import java.util.List;

public class ErrorResponse {
    private String developerErrorMessage;
    private List<String> errors;
    
    // Constructors
    public ErrorResponse() {}
    
    public ErrorResponse(String developerErrorMessage, List<String> errors) {
        this.developerErrorMessage = developerErrorMessage;
        this.errors = errors;
    }
    
    // Getters and Setters
    public String getDeveloperErrorMessage() { return developerErrorMessage; }
    public void setDeveloperErrorMessage(String developerErrorMessage) { 
        this.developerErrorMessage = developerErrorMessage; 
    }
    
    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }
}

// src/main/java/com/awarex/service/GoRestService.java
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

// src/main/java/com/awarex/controller/PostController.java
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

// src/main/java/com/awarex/config/WebConfig.java
package com.awarex.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.awarex")
public class WebConfig implements WebMvcConfigurer {
}

// src/main/webapp/WEB-INF/web.xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee 
         http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    
    <display-name>Backend Assignment</display-name>
    
    <servlet>
        <servlet-name>dispatcher</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextClass</param-name>
            <param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
        </init-param>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>com.awarex.config.WebConfig</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    
    <servlet-mapping>
        <servlet-name>dispatcher</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>