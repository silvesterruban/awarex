package com.awarex.service;

import com.awarex.model.Post;
import com.awarex.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class GoRestServiceTest {

    @Mock
    private Client client;
    
    @Mock
    private WebTarget webTarget;
    
    @Mock
    private Invocation.Builder requestBuilder;
    
    @Mock
    private Response response;
    
    private GoRestService goRestService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        goRestService = new GoRestService(client, objectMapper);
        
        // Setup common mock behavior
        when(client.target(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(requestBuilder);
        when(webTarget.queryParam(anyString(), any())).thenReturn(webTarget);
        when(requestBuilder.header(anyString(), any())).thenReturn(requestBuilder);
    }

    @Test
    void createUser_Success() throws Exception {
        // Arrange
        String name = "John Doe";
        String email = "john.doe@example.com";
        String gender = "male";
        
        User expectedUser = new User();
        expectedUser.setId(123L);
        expectedUser.setName(name);
        expectedUser.setEmail(email);
        expectedUser.setGender(gender);
        expectedUser.setStatus("active");
        
        when(requestBuilder.post(any(Entity.class))).thenReturn(response);
        when(response.getStatusInfo()).thenReturn(Response.Status.OK);
        when(response.readEntity(String.class)).thenReturn(
            objectMapper.writeValueAsString(expectedUser)
        );
        
        // Act
        User result = goRestService.createUser(name, email, gender);
        
        // Assert
        assertNotNull(result);
        assertEquals(expectedUser.getId(), result.getId());
        assertEquals(expectedUser.getName(), result.getName());
        assertEquals(expectedUser.getEmail(), result.getEmail());
        assertEquals(expectedUser.getGender(), result.getGender());
        assertEquals(expectedUser.getStatus(), result.getStatus());
    }

    @Test
    void createPost_Success() throws Exception {
        // Arrange
        Long userId = 123L;
        String title = "Test Title";
        String body = "Test Body";
        
        Post expectedPost = new Post();
        expectedPost.setId(456L);
        expectedPost.setUserId(userId);
        expectedPost.setTitle(title);
        expectedPost.setBody(body);
        
        when(requestBuilder.post(any(Entity.class))).thenReturn(response);
        when(response.getStatusInfo()).thenReturn(Response.Status.OK);
        when(response.readEntity(String.class)).thenReturn(
            objectMapper.writeValueAsString(expectedPost)
        );
        
        // Act
        Post result = goRestService.createPost(userId, title, body);
        
        // Assert
        assertNotNull(result);
        assertEquals(expectedPost.getId(), result.getId());
        assertEquals(expectedPost.getUserId(), result.getUserId());
        assertEquals(expectedPost.getTitle(), result.getTitle());
        assertEquals(expectedPost.getBody(), result.getBody());
    }

    @Test
    void findUserByEmail_UserFound() throws Exception {
        // Arrange
        String email = "test@example.com";
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setName("Test User");
        expectedUser.setEmail(email);
        
        when(requestBuilder.get()).thenReturn(response);
        when(response.getStatus()).thenReturn(200);
        when(response.readEntity(String.class)).thenReturn(
            String.format("[%s]", objectMapper.writeValueAsString(expectedUser))
        );
        
        // Act
        User result = goRestService.findUserByEmail(email);
        
        // Assert
        assertNotNull(result);
        assertEquals(expectedUser.getId(), result.getId());
        assertEquals(expectedUser.getName(), result.getName());
        assertEquals(expectedUser.getEmail(), result.getEmail());
    }

    @Test
    void findUserByEmail_UserNotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        
        when(requestBuilder.get()).thenReturn(response);
        when(response.getStatus()).thenReturn(200);
        when(response.readEntity(String.class)).thenReturn("[]");
        
        // Act
        User result = goRestService.findUserByEmail(email);
        
        // Assert
        assertNull(result);
    }

    @Test
    void getAllUsers_Success() throws Exception {
        // Arrange
        User user1 = new User();
        user1.setId(1L);
        user1.setName("User 1");
        
        User user2 = new User();
        user2.setId(2L);
        user2.setName("User 2");
        
        List<User> expectedUsers = Arrays.asList(user1, user2);
        
        when(requestBuilder.get()).thenReturn(response);
        when(response.getStatusInfo()).thenReturn(Response.Status.OK);
        when(response.readEntity(String.class)).thenReturn(
            objectMapper.writeValueAsString(expectedUsers)
        );
        
        // Act
        List<User> result = goRestService.getAllUsers();
        
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("User 1", result.get(0).getName());
        assertEquals(2L, result.get(1).getId());
        assertEquals("User 2", result.get(1).getName());
    }

    @Test
    void getAllPosts_Success() throws Exception {
        // Arrange
        Post post1 = new Post();
        post1.setId(1L);
        post1.setTitle("Post 1");
        
        Post post2 = new Post();
        post2.setId(2L);
        post2.setTitle("Post 2");
        
        List<Post> expectedPosts = Arrays.asList(post1, post2);
        
        when(requestBuilder.get()).thenReturn(response);
        when(response.getStatusInfo()).thenReturn(Response.Status.OK);
        when(response.readEntity(String.class)).thenReturn(
            objectMapper.writeValueAsString(expectedPosts)
        );
        
        // Act
        List<Post> result = goRestService.getAllPosts();
        
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Post 1", result.get(0).getTitle());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Post 2", result.get(1).getTitle());
    }
}
