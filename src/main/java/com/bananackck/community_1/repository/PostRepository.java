package com.bananackck.community_1.repository;

import com.bananackck.community_1.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 기본키(id)로 조회하는 경우
    Optional<Post> findById(Long id);

    // 또는, 특정 User의 Post를 조회하려면
    List<Post> findAllByUserId(Long userId);
}
