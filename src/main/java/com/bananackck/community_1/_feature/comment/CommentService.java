package com.bananackck.community_1._feature.comment;

import com.bananackck.community_1._feature.post.entity.Post;
import com.bananackck.community_1._feature.post.repository.PostRepository;
import com.bananackck.community_1._feature.user.User;
import com.bananackck.community_1._feature.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository  commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public List<CommentDto> findAllByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId)
                .stream()
                .map(CommentDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto createComment(CommentDto req, Long postId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id=" + postId));

        Comment comment = Comment.builder()
                .user(user)
                .text(req.getText())
                .createdAt(LocalDateTime.now())
                .post(post)
                .build();
        commentRepository.save(comment);

        return CommentDto.builder()
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .userNickname(user.getNickname())
                .userProfileImg(user.getProfilePicture())
                .build();
    }
}
