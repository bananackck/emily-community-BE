package com.bananackck.community_1._feature.post.service;

import com.bananackck.community_1._feature.comment.CommentDto;
import com.bananackck.community_1._feature.post.dto.CreatePostRequestDto;
import com.bananackck.community_1._feature.post.dto.PostDetailDto;
import com.bananackck.community_1._feature.post.dto.PostDto;
import com.bananackck.community_1._feature.post.dto.UpdatePostRequestDto;
import com.bananackck.community_1._feature.post.entity.Post;
import com.bananackck.community_1._feature.user.User;
import com.bananackck.community_1._feature.comment.CommentRepository;
import com.bananackck.community_1._feature.post.repository.PostLikeRepository;
import com.bananackck.community_1._feature.post.repository.PostRepository;
import com.bananackck.community_1._feature.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public List<PostDto> findAll() {
        List<Post> posts = postRepository.findAll();

        return posts.stream().map(post -> {
            long likeCount = postLikeRepository.countByPostId(post.getId());
            long commentCount = commentRepository.countByPostId(post.getId());

            return PostDto.builder()
//                    .id(post.getId())
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
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id=" + postId));

        long likeCount = postLikeRepository.countByPostId(post.getId());

        // comment 리스트
        List<CommentDto> commentDtos = post.getComments().stream().map(comment ->
                CommentDto.builder()
//                        .id(comment.getId())
                        .text(comment.getText())
                        .createdAt(comment.getCreatedAt())
                        .userNickname(comment.getUser() != null ? comment.getUser().getNickname() : null)
                        .userProfileImg(comment.getUser() != null ? comment.getUser().getProfilePicture() : null)
                        .build()
        ).collect(Collectors.toList());

        return PostDetailDto.builder()
//                .id(post.getId())
                .title(post.getTitle())
                .text(post.getText())
                .createdAt(post.getCreatedAt())
                .viewCount(post.getViewCount() != null ? post.getViewCount() : 0L)
                .likeCount(likeCount)
                .commentCount((long) commentDtos.size())
                .userNickname(post.getUser() != null ? post.getUser().getNickname() : null)
                .userProfileImg(post.getUser() != null ? post.getUser().getProfilePicture() : null)
                .comments(commentDtos)
                .build();
    }


    //게시글 생성
    @Transactional
    public PostDto createPost(CreatePostRequestDto req, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        Post post = Post.builder()
                .user(user)
                .title(req.getTitle())
                .text(req.getText())
                .build();

        post.setCreatedAt(LocalDateTime.now());
        post.setViewCount(0L);

        Post saved = postRepository.save(post);

        long likeCount = postLikeRepository.countByPostId(saved.getId());
        long commentCount = commentRepository.countByPostId(saved.getId());

        return PostDto.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .text(saved.getText())
                .likeCount(likeCount)
                .createdAt(saved.getCreatedAt())
                .viewCount(saved.getViewCount())
                .commentCount(commentCount)
                .userNickname(user.getNickname())
                .userProfileImg(user.getProfilePicture())
                .build();
    }

    //게시물 수정
    @Transactional
    public PostDto updatePost(Long postId, Long userId, UpdatePostRequestDto req) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id=" + postId));
        if(!Objects.equals(post.getUser().getId(), userId)){
            throw new IllegalArgumentException("User is not the owner of the post");
        }

        if (req.getTitle() != null) post.setTitle(req.getTitle());
        if (req.getText() != null) post.setText(req.getText());
        if (req.getImg() != null) post.setImg(req.getImg());

        Post updated = postRepository.save(post);

        return PostDto.builder()
//                .id(updated.getId())
                .title(updated.getTitle())
                .text(updated.getText())
                .img(updated.getImg())
                .build();
    }

    //게시물 삭제
    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id=" + postId));

        if(!Objects.equals(post.getUser().getId(), userId)){
            throw new IllegalArgumentException("User is not the owner of the post");
        }
        postRepository.deleteById(postId);
    }

}