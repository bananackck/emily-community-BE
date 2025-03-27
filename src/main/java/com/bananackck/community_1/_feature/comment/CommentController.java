package com.bananackck.community_1._feature.comment;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    //댓글 조회
    @GetMapping("/comments")
    public List<CommentDto.ViewCommentDto> getComments(@PathVariable Long postId) {
        return commentService.findAllByPostId(postId);
    }

    //댓글 생성
    @PostMapping("/comments")
    public List<CommentDto.ViewCommentDto> createComment(
            @PathVariable Long postId,
            @RequestBody CommentDto.CreateCommentDto req,
            @AuthenticationPrincipal Jwt jwt) {

        Long userId = Long.valueOf(jwt.getSubject());
        CommentDto.ViewCommentDto created = commentService.createComment(req, postId, userId);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return commentService.findAllByPostId(postId);
//        return ResponseEntity.created(location).body(created);
    }

    //댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public List<CommentDto.ViewCommentDto> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal Jwt jwt) {

        Long userId = Long.valueOf(jwt.getSubject());
        commentService.deleteComment(postId, commentId, userId);
        return commentService.findAllByPostId(postId);
    }

    //댓글 수정
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto.ViewCommentDto> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentDto.UpdateCommentDto req,
            @AuthenticationPrincipal Jwt jwt) {

        Long userId = Long.valueOf(jwt.getSubject());
        CommentDto.ViewCommentDto updated = commentService.updateComment(postId, commentId, userId, req);
        return ResponseEntity.ok(updated);
    }

}
