package com.bananackck.community_1._feature.post.repository;

import com.bananackck.community_1._feature.post.entity.PostLike;
import com.bananackck.community_1._feature.post.entity.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);
    long countByPostId(Long postId);


}
