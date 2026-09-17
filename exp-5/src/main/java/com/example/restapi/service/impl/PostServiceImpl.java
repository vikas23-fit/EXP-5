package com.example.restapi.service.impl;

import com.example.restapi.dto.PostRequest;
import com.example.restapi.dto.PostResponse;
import com.example.restapi.entity.Post;
import com.example.restapi.exception.ResourceNotFoundException;
import com.example.restapi.repository.PostRepository;
import com.example.restapi.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private static final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostResponse createPost(PostRequest request) {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(request.getAuthor())
                .build();

        Post savedPost = postRepository.save(post);
        log.info("Processing request for Post ID: {}", savedPost.getId());
        return mapToResponse(savedPost);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        log.info("Processing request to retrieve all posts");
        return postRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse getPostById(Long id) {
        log.info("Processing request for Post ID: {}", id);
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        return mapToResponse(post);
    }

    @Override
    public PostResponse updatePost(Long id, PostRequest request) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));

        existingPost.setTitle(request.getTitle());
        existingPost.setContent(request.getContent());
        existingPost.setAuthor(request.getAuthor());

        Post updatedPost = postRepository.save(existingPost);
        return mapToResponse(updatedPost);
    }

    @Override
    public void deletePost(Long id) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        postRepository.delete(existingPost);
    }

    private PostResponse mapToResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
