package com.bananackck.community_1._feature.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일로 회원 조회 (예시)
    Optional<User> findByEmail(String email);

    Optional<Object> findByNickname(String nickname);
}
