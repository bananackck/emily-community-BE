package com.bananackck.community_1._feature.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    long countByPostId(Long postId);
    List<Comment> findAllByPostId(Long postId);
    Optional<Comment> findByIdAndPostId(Long id, Long postId);

}
