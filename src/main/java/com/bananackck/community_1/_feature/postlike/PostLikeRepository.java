package com.bananackck.community_1._feature.postlike;

import com.bananackck.community_1._feature.postlike.entity.PostLike;
import com.bananackck.community_1._feature.postlike.entity.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);
    long countByPostId(Long postId);
    void deleteByUserIdAndPostId(Long userId, Long postId);
}

