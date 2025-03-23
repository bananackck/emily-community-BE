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

    //댓글 조회
    public List<CommentDto.ViewCommentDto> findAllByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId)
                .stream()
                .map(CommentDto::fromEntity)
                .collect(Collectors.toList());
    }

    //댓글 생성
    @Transactional
    public CommentDto.ViewCommentDto createComment(CommentDto.CreateCommentDto req, Long postId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id=" + postId));

        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .text(req.getText())
                .createdAt(LocalDateTime.now())
                .build();

        Comment saved = commentRepository.save(comment);
        return CommentDto.fromEntity(saved);
    }
}
