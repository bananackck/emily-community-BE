package com.bananackck.community_1.service;

import com.bananackck.community_1.dto.CommentDto;
import com.bananackck.community_1.dto.PostDetailDto;
import com.bananackck.community_1.dto.PostDto;
import com.bananackck.community_1.entity.Post;
import com.bananackck.community_1.repository.CommentRepository;
import com.bananackck.community_1.repository.PostLikeRepository;
import com.bananackck.community_1.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;

    public List<PostDto> findAll() {
        List<Post> posts = postRepository.findAll();

        return posts.stream().map(post -> {
            long likeCount = postLikeRepository.countByPostId(post.getId());
            long commentCount = commentRepository.countByPostId(post.getId());

            return PostDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .text(post.getText())
                    .createdAt(post.getCreatedAt())
                    .viewCount(post.getViewCount())
                    .likeCount(likeCount)
                    .commentCount(commentCount)
                    .userNickname(post.getUser() != null ? post.getUser().getNickname() : null)
                    .userProfileImg(post.getUser() != null ? post.getUser().getProfilePicture() : null)
                    .build();
        }).collect(Collectors.toList());
    }

    @Transactional
    public PostDetailDto findDetailById(long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        long likeCount = postLikeRepository.countByPostId(post.getId());

        // comment 리스트
        List<CommentDto> commentDtos = post.getComments().stream().map(comment ->
                CommentDto.builder()
                        .id(comment.getId())
                        .text(comment.getText())
                        .createdAt(comment.getCreatedAt())
                        .userNickname(comment.getUser() != null ? comment.getUser().getNickname() : null)
                        .build()
        ).collect(Collectors.toList());

        return PostDetailDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .text(post.getText())
                .createdAt(post.getCreatedAt())
                .viewCount(post.getViewCount() != null ? Long.valueOf(post.getViewCount()) : 0L)
                .likeCount(likeCount)
                .userNickname(post.getUser() != null ? post.getUser().getNickname() : null)
                .userProfileImg(post.getUser() != null ? post.getUser().getProfilePicture() : null)
                .comments(commentDtos)
                .build();
    }
}