package com.example.restapi.service;

import com.example.restapi.dto.PostRequest;
import com.example.restapi.dto.PostResponse;

import java.util.List;

public interface PostService {

    PostResponse createPost(PostRequest request);

    List<PostResponse> getAllPosts();

    PostResponse getPostById(Long id);

    PostResponse updatePost(Long id, PostRequest request);

    void deletePost(Long id);
}
