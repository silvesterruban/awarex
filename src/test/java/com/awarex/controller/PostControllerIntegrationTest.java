package com.awarex.controller;

import com.awarex.config.WebConfig;
import com.awarex.dto.ErrorResponse;
import com.awarex.dto.WritePostRequest;
import com.awarex.exception.GlobalExceptionHandler;
import com.awarex.model.Post;
import com.awarex.model.User;
import com.awarex.service.GoRestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
class PostControllerIntegrationTest {

    private MockMvc mockMvc;

    @Mock
    private GoRestService goRestService;

    @InjectMocks
    private PostController postController;

    private User testUser;
    private Post testPost;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Setup test data
        testUser = new User(1L, "Test User", "test@example.com", "male", "active");
        testPost = new Post(101L, 1L, "Test Title", "Test body content");
        objectMapper = new ObjectMapper();
        
        // Initialize mockMvc with the controller and exception handler
        this.mockMvc = MockMvcBuilders.standaloneSetup(postController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void writePost_NewUser_Success() throws Exception {
        // Arrange
        when(goRestService.findUserByEmail(anyString())).thenReturn(null);
        when(goRestService.createUser(anyString(), anyString(), anyString())).thenReturn(testUser);
        when(goRestService.createPost(anyLong(), anyString(), anyString())).thenReturn(testPost);

        String requestJson = "{\"userName\":\"Test User\",\"userEmail\":\"test@example.com\","
            + "\"postTitle\":\"Test Title\",\"postBody\":\"Test body content\"}";

        // Act & Assert
        mockMvc.perform(post("/api/writeAPost")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Test User"))
                .andExpect(jsonPath("$.userEmail").value("test@example.com"))
                .andExpect(jsonPath("$.postTitle").value("Test Title"))
                .andExpect(jsonPath("$.postBody").value("Test body content"));

        // Verify service interactions
        verify(goRestService, times(1)).findUserByEmail("test@example.com");
        verify(goRestService, times(1)).createUser("Test User", "test@example.com", "male");
        verify(goRestService, times(1)).createPost(1L, "Test Title", "Test body content");
    }

    @Test
    void writePost_ExistingUser_Success() throws Exception {
        // Arrange
        when(goRestService.findUserByEmail(anyString())).thenReturn(testUser);
        when(goRestService.createPost(anyLong(), anyString(), anyString())).thenReturn(testPost);

        String requestJson = "{\"userName\":\"Test User\",\"userEmail\":\"test@example.com\","
            + "\"postTitle\":\"Test Title\",\"postBody\":\"Test body content\"}";

        // Act & Assert
        mockMvc.perform(post("/api/writeAPost")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Test User"))
                .andExpect(jsonPath("$.userEmail").value("test@example.com"));

        // Verify service interactions
        verify(goRestService, times(1)).findUserByEmail("test@example.com");
        verify(goRestService, never()).createUser(anyString(), anyString(), anyString());
        verify(goRestService, times(1)).createPost(1L, "Test Title", "Test body content");
    }

    @Test
    void writePost_InvalidUserData_ReturnsErrorResponse() throws Exception {
        // Arrange
        String invalidRequestJson = "{\"name\":\"\",\"email\":\"invalid-email\"}";

        // Act & Assert
        mockMvc.perform(post("/api/writeAPost")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details[0]").exists());

        // Verify no service interactions for invalid input
        verify(goRestService, never()).findUserByEmail(anyString());
        verify(goRestService, never()).createUser(anyString(), anyString(), anyString());
        verify(goRestService, never()).createPost(anyLong(), anyString(), anyString());
    }

    @Test
    void writePost_InternalServerError_ReturnsErrorResponse() throws Exception {
        // Arrange
        when(goRestService.findUserByEmail(anyString()))
            .thenThrow(new RuntimeException("Unexpected error"));

        String requestJson = "{\"userName\":\"Test User\",\"userEmail\":\"test@example.com\","
            + "\"postTitle\":\"Test Title\",\"postBody\":\"Test body content\"}";

        // Act & Assert
        mockMvc.perform(post("/api/writeAPost")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error").value(containsString("Error processing request")));

        // Verify service interaction
        verify(goRestService, times(1)).findUserByEmail("test@example.com");
        verify(goRestService, never()).createUser(anyString(), anyString(), anyString());
        verify(goRestService, never()).createPost(anyLong(), anyString(), anyString());
    }
}
