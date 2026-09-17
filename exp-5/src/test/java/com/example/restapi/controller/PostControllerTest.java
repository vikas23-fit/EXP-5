package com.example.restapi.controller;

import com.example.restapi.dto.PostRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Assignment 1 & 4: Test Create Post API and Response Standardization")
    void createPost_ValidRequest_ReturnsCreated() throws Exception {
        PostRequest request = PostRequest.builder()
                .title("Test REST API")
                .content("Valid content for testing REST API endpoints.")
                .author("Author Name")
                .build();

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("X-Correlation-ID"))
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", containsString("Post created")))
                .andExpect(jsonPath("$.data.title", is("Test REST API")));
    }

    @Test
    @DisplayName("Assignment 2 & 4: Test Bean Validation Failure (Invalid Input)")
    void createPost_InvalidRequest_ReturnsBadRequest() throws Exception {
        PostRequest request = PostRequest.builder()
                .title("")
                .content("")
                .author("")
                .build();

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", containsString("Validation failed")))
                .andExpect(jsonPath("$.errors.content", is("Content must not be empty")));
    }

    @Test
    @DisplayName("Assignment 1 & 5: Test Get Post By Id and MDC Correlation Tracing")
    void getPostById_NotFound_Returns404() throws Exception {
        mockMvc.perform(get("/api/posts/99999")
                        .header("X-Correlation-ID", "custom-trace-12345"))
                .andExpect(status().isNotFound())
                .andExpect(header().string("X-Correlation-ID", "custom-trace-12345"))
                .andExpect(jsonPath("$.message", containsString("Post not found with id: 99999")))
                .andExpect(jsonPath("$.correlationId", is("custom-trace-12345")));
    }
}
