package com.example.restapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequest {

    @NotBlank(message = "Title is required and cannot be blank")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @NotBlank(message = "Content must not be empty")
    @Size(max = 280, message = "Content exceeds limit")
    private String content;

    @NotBlank(message = "Author is required and cannot be blank")
    @Size(max = 50, message = "Author name must not exceed 50 characters")
    private String author;
}
