package com.bananackck.community_1.controller;

import com.bananackck.community_1.dto.PostDetailDto;
import com.bananackck.community_1.dto.PostDto;
import com.bananackck.community_1.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, String> payload) {
//        String title = payload.get("title");
//        String text = payload.get("text");
        Map<String, Object> response = Map.of("message", "Post created successfully", "post", payload);
        return ResponseEntity.ok(response);
//        return ResponseEntity.of(HttpStatus.CREATED, "ok");

    }
}

