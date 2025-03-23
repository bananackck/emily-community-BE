package com.bananackck.community_1._feature.post.repository;

import com.bananackck.community_1._feature.post.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    long countByPostId(Long postId);
    List<Comment> findAllByPostId(Long postId);
}
