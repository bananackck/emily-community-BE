package com.bananackck.community_1.repository;

import com.bananackck.community_1.entity.PostLike;
import com.bananackck.community_1.entity.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);
    long countByPostId(Long postId);


}
