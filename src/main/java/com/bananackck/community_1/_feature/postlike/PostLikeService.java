package com.bananackck.community_1._feature.postlike;

import com.bananackck.community_1._feature.post.entity.Post;
import com.bananackck.community_1._feature.post.repository.PostRepository;
import com.bananackck.community_1._feature.postlike.entity.PostLike;
import com.bananackck.community_1._feature.user.User;
import com.bananackck.community_1._feature.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostLikeService(PostLikeRepository postLikeRepository, PostRepository postRepository, UserRepository userRepository) {
        this.postLikeRepository = postLikeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public LikeDto createLike(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id=" + postId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        // 이미 좋아요가 있는지 확인
        if (postLikeRepository.existsByUserIdAndPostId(userId, postId)) {
            deleteLike(postId, userId);
            return LikeDto.builder()
                    .likeCount(postLikeRepository.countByPostId(postId))
                    .postId(postId)
                    .userId(userId)
                    .build();
//            return
//            throw new IllegalStateException("이미 좋아요를 누른 게시물입니다.");
        }

        // 좋아요 생성
        PostLike like = PostLike.builder()
                .post(post)
                .user(user)
                .build();

        postLikeRepository.save(like);
        long likeCount = postLikeRepository.countByPostId(postId);

        return LikeDto.builder()
                .likeCount(likeCount)
                .postId(postId)
                .userId(userId)
                .build();
    }

    //좋아요 취소
    @Transactional
    public void deleteLike(Long postId, Long userId) {
        postLikeRepository.deleteByUserIdAndPostId(userId, postId);
    }
}
