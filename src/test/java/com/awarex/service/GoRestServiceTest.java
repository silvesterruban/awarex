package com.awarex.service;

import com.awarex.model.Post;
import com.awarex.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GoRestServiceTest {

    @Mock
    private Client client;
    
    @Mock
    private WebTarget webTarget;
    
    @Mock
    private Invocation.Builder builder;
    
    @Mock
    private Response response;
    
    private GoRestService goRestService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws Exception {
        // Initialize the service
        goRestService = new GoRestService();
        
        // Inject mocks using reflection
        injectMock(goRestService, "client", client);
        injectMock(goRestService, "objectMapper", objectMapper);
        
        // Set up the mock client to return our mock webTarget
        lenient().when(client.target(anyString())).thenReturn(webTarget);
        lenient().when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        lenient().when(builder.header(anyString(), anyString())).thenReturn(builder);
    }
    
    private void injectMock(Object target, String fieldName, Object mock) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, mock);
    }

    @Test
    void testCreateUser_Success() throws Exception {
        // Arrange - Using user_id instead of id to match the User model
        String responseJson = "{\"user_id\":1,\"name\":\"Test User\",\"email\":\"test@example.com\",\"gender\":\"male\",\"status\":\"active\"}";
        when(response.readEntity(String.class)).thenReturn(responseJson);
        when(response.getStatus()).thenReturn(201);
        when(builder.post(any(Entity.class))).thenReturn(response);
        
        // Act
        User user = goRestService.createUser("Test User", "test@example.com", "male");
        
        // Assert
        assertNotNull(user);
        assertEquals(1L, user.getUserId());
        assertEquals("Test User", user.getUserName());
        assertEquals("test@example.com", user.getUserEmail());
        
        // Verify
        verify(client).target(contains("users"));
        verify(builder).post(any(Entity.class));
    }

    @Test
    void testCreatePost_Success() throws Exception {
        // Arrange
        String responseJson = "{\"id\":101,\"user_id\":1,\"title\":\"Test Title\",\"body\":\"Test body content\"}";
        when(response.readEntity(String.class)).thenReturn(responseJson);
        when(response.getStatus()).thenReturn(201);
        when(builder.post(any(Entity.class))).thenReturn(response);
        
        // Act
        Post post = goRestService.createPost(1L, "Test Title", "Test body content");
        
        // Assert
        assertNotNull(post);
        assertEquals(101L, post.getId());
        assertEquals(1L, post.getUserId());
        assertEquals("Test Title", post.getPostTitle());
        assertEquals("Test body content", post.getPostBody());
        
        // Verify
        verify(client).target(contains("posts"));
        verify(builder).post(any(Entity.class));
    }

    @Test
    void testFindUserByEmail_UserExists() throws Exception {
        // Arrange - Using user_id instead of id to match the User model
        String responseJson = "[{\"user_id\":1,\"name\":\"Test User\",\"email\":\"test@example.com\",\"gender\":\"male\",\"status\":\"active\"}]";
        when(response.readEntity(String.class)).thenReturn(responseJson);
        when(response.getStatus()).thenReturn(200);
        when(builder.get()).thenReturn(response);
        when(webTarget.queryParam(eq("email"), anyString())).thenReturn(webTarget);
        
        // Act
        User user = goRestService.findUserByEmail("test@example.com");
        
        // Assert
        assertNotNull(user);
        assertEquals(1L, user.getUserId());
        assertEquals("Test User", user.getUserName());
        assertEquals("test@example.com", user.getUserEmail());
        
        // Verify
        verify(webTarget).queryParam("email", "test@example.com");
        verify(builder).get();
    }

    @Test
    void testFindUserByEmail_UserNotFound() throws Exception {
        // Arrange
        when(response.readEntity(String.class)).thenReturn("[]");
        when(response.getStatus()).thenReturn(200);
        when(builder.get()).thenReturn(response);
        when(webTarget.queryParam(eq("email"), anyString())).thenReturn(webTarget);
        
        // Act
        User user = goRestService.findUserByEmail("nonexistent@example.com");
        
        // Assert
        assertNull(user);
        
        // Verify
        verify(webTarget).queryParam("email", "nonexistent@example.com");
        verify(builder).get();
    }
}
