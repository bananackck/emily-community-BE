package com.bananackck.community_1._feature.postlike;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/like")
@RequiredArgsConstructor
public class PostLikeController {
    private final PostLikeService postLikeService;

    //좋아요 누르기
    @PostMapping
    public LikeDto likePost(@PathVariable Long postId, @AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.valueOf(jwt.getSubject());
        return postLikeService.createLike(postId, userId);
    }

    //좋아요 취소
    @DeleteMapping
    public void unlikePost(@PathVariable Long postId, @AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.valueOf(jwt.getSubject());
        postLikeService.deleteLike(postId, userId);
    }
}
