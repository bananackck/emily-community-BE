package com.bananackck.community_1._feature.post.controller;

import com.bananackck.community_1._feature.post.dto.CreatePostRequestDto;
import com.bananackck.community_1._feature.post.dto.PostDetailDto;
import com.bananackck.community_1._feature.post.dto.PostDto;
import com.bananackck.community_1._feature.post.dto.UpdatePostRequestDto;
import com.bananackck.community_1._feature.post.service.PostService;
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

    //게시물 생성
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

    //게시물 수정
    @PatchMapping("/{postId}")
    public ResponseEntity<PostDto> update(@PathVariable Long postId, @RequestBody UpdatePostRequestDto request) {
        PostDto updated = postService.updatePost(postId, request);

        return ResponseEntity.ok(updated);
    }
}