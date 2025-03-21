package com.bananackck.community_1.controller;

import com.bananackck.community_1.dto.CreatePostRequestDto;
import com.bananackck.community_1.dto.PostDetailDto;
import com.bananackck.community_1.dto.PostDto;
import com.bananackck.community_1.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    //모든 게시물 조회 api
    @GetMapping
    public List<PostDto> findAll() {
        return postService.findAll();
    }

    //특정 id 게시물 조회 api
    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailDto> getPostDetail(@PathVariable Long postId) {
        PostDetailDto detailDto = postService.findDetailById(postId);
        return ResponseEntity.ok(detailDto);
    }

    //post api
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody CreatePostRequestDto request) {
        PostDto created = postService.createPost(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

}

