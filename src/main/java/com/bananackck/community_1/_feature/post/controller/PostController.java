package com.bananackck.community_1._feature.post.controller;

import com.bananackck.community_1._feature.post.dto.CreatePostRequestDto;
import com.bananackck.community_1._feature.post.dto.PostDetailDto;
import com.bananackck.community_1._feature.post.dto.PostDto;
import com.bananackck.community_1._feature.post.dto.UpdatePostRequestDto;
import com.bananackck.community_1._feature.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@Slf4j
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
    public ResponseEntity<PostDetailDto> getPostDetail(
            @PathVariable Long postId,
            @AuthenticationPrincipal Jwt jwt) {
        long userId = Long.parseLong(jwt.getSubject());
        PostDetailDto detailDto = postService.findDetailById(postId, userId);
        return ResponseEntity.ok(detailDto);
    }

    //게시물 생성
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDto> createPost(
            @RequestPart("data") CreatePostRequestDto request,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal Jwt jwt) throws IOException {

        Long userId = Long.valueOf(jwt.getSubject());
        PostDto created = postService.createPost(request, userId, file);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }


    //게시물 수정
    @PatchMapping(value="/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDto> updatePost(
            @PathVariable Long postId,
            @RequestPart("data") UpdatePostRequestDto request,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal Jwt jwt) throws IOException {
        Long userId = Long.valueOf(jwt.getSubject());
        PostDto updated = postService.updatePost(postId, userId, request, file);

        return ResponseEntity.ok(updated);
    }

    //게시물 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.valueOf(jwt.getSubject());
        postService.deletePost(postId, userId);
        return ResponseEntity.noContent().build();
    }
}