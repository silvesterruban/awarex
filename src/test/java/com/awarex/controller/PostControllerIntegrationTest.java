package com.awarex.controller;

import com.awarex.config.WebConfig;
import com.awarex.dto.WritePostRequest;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
public class PostControllerIntegrationTest {

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
        // Initialize test data
        testUser = new User(1L, "Test User", "test@example.com", "male", "active");
        testPost = new Post(101L, 1L, "Test Title", "Test body content");
        objectMapper = new ObjectMapper();
        
        // Setup MockMvc to use our controller
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    void writePost_NewUser_Success() throws Exception {
        // Mock service responses
        when(goRestService.findUserByEmail(anyString())).thenReturn(null);
        when(goRestService.createUser(anyString(), anyString(), anyString())).thenReturn(testUser);
        when(goRestService.createPost(anyLong(), anyString(), anyString())).thenReturn(testPost);

        String requestJson = "{\"name\":\"Test User\",\"email\":\"test@example.com\",\"gender\":\"male\","
                + "\"title\":\"Test Title\",\"body\":\"Test body content\"}";

        // Act & Assert
        mockMvc.perform(post("/api/writeAPost")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Test User"))
                .andExpect(jsonPath("$.userEmail").value("test@example.com"))
                .andExpect(jsonPath("$.userGender").value("male"))
                .andExpect(jsonPath("$.postTitle").value("Test Title"))
                .andExpect(jsonPath("$.postBody").value("Test body content"));
    }

    @Test
    void writePost_ExistingUser_Success() throws Exception {
        // Mock service responses
        when(goRestService.findUserByEmail(anyString())).thenReturn(testUser);
        when(goRestService.createPost(anyLong(), anyString(), anyString())).thenReturn(testPost);

        String requestJson = "{\"name\":\"Test User\",\"email\":\"test@example.com\",\"gender\":\"male\","
                + "\"title\":\"Test Title\",\"body\":\"Test body content\"}";

        // Act & Assert
        mockMvc.perform(post("/api/writeAPost")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Test User"))
                .andExpect(jsonPath("$.userEmail").value("test@example.com"))
                .andExpect(jsonPath("$.userGender").value("male"))
                .andExpect(jsonPath("$.postTitle").value("Test Title"))
                .andExpect(jsonPath("$.postBody").value("Test body content"));
    }
}
