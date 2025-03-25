package com.bananackck.community_1._feature.post.service;

import com.bananackck.community_1._feature.comment.CommentDto;
import com.bananackck.community_1._feature.post.dto.CreatePostRequestDto;
import com.bananackck.community_1._feature.post.dto.PostDetailDto;
import com.bananackck.community_1._feature.post.dto.PostDto;
import com.bananackck.community_1._feature.post.dto.UpdatePostRequestDto;
import com.bananackck.community_1._feature.post.entity.Post;
import com.bananackck.community_1._feature.user.User;
import com.bananackck.community_1._feature.comment.CommentRepository;
import com.bananackck.community_1._feature.postlike.PostLikeRepository;
import com.bananackck.community_1._feature.post.repository.PostRepository;
import com.bananackck.community_1._feature.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    //게시물 목록 조회
    public List<PostDto> findAll() {
        List<Post> posts = postRepository.findAll();

        return posts.stream().map(post -> {
            long likeCount = postLikeRepository.countByPostId(post.getId());
            long commentCount = commentRepository.countByPostId(post.getId());

            return PostDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .text(post.getText())
                    .createdAt(post.getCreatedAt())
                    .viewCount(post.getViewCount())
                    .likeCount(likeCount)
                    .commentCount(commentCount)
                    .userNickname(post.getUser() != null ? post.getUser().getNickname() : null)
                    .userProfileImg(post.getUser() != null ? post.getUser().getProfilePicture() : null)
                    .build();
        }).collect(Collectors.toList());
    }

    //게시물 상세 조회
    @Transactional
    public PostDetailDto findDetailById(long postId, long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id=" + postId));

        post.setViewCount(post.getViewCount() != null ? post.getViewCount()+1 : 0L);
        long likeCount = postLikeRepository.countByPostId(post.getId());

        // comment 리스트
        List<CommentDto.ViewCommentDto> commentDtos = post.getComments().stream().map(comment ->
                CommentDto.ViewCommentDto.builder()
                        .id(comment.getId())
                        .text(comment.getText())
                        .createdAt(comment.getCreatedAt())
                        .userNickname(comment.getUser() != null ? comment.getUser().getNickname() : null)
                        .userProfileImg(comment.getUser() != null ? comment.getUser().getProfilePicture() : null)
                        .build()
        ).collect(Collectors.toList());

        boolean liked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        return PostDetailDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .text(post.getText())
                .img(post.getImg())
                .createdAt(post.getCreatedAt())
                .viewCount(post.getViewCount() != null ? post.getViewCount() : 0L)
                .likeCount(likeCount)
                .isLiked(liked)
                .commentCount((long) commentDtos.size())
                .userNickname(post.getUser() != null ? post.getUser().getNickname() : null)
                .userProfileImg(post.getUser() != null ? post.getUser().getProfilePicture() : null)
                .comments(commentDtos)
                .build();
    }

    @Value("${file.upload-dir}")
    private String uploadDir;
    //게시글 생성
    @Transactional
    public PostDto createPost(CreatePostRequestDto req, Long userId, MultipartFile imgFile) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        // 이미지 업로드
        String uploadPath = Paths.get(uploadDir).toAbsolutePath().toString();

        // 파일명 난수화
        String imgFileNameEncrypt = UUID.randomUUID() + "_" + StringUtils.cleanPath(imgFile.getOriginalFilename());
        File dest = new File(uploadPath, imgFileNameEncrypt);
        imgFile.transferTo(dest);
        String fileUrl = "/assets/img/data/" + imgFileNameEncrypt;

        Post post = Post.builder()
                .user(user)
                .title(req.getTitle())
                .text(req.getText())
                .img(fileUrl)
                .build();

        post.setCreatedAt(LocalDateTime.now());
        post.setViewCount(0L);

        Post saved = postRepository.save(post);

        long likeCount = postLikeRepository.countByPostId(saved.getId());
        long commentCount = commentRepository.countByPostId(saved.getId());

        return PostDto.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .text(saved.getText())
                .img(saved.getImg())
                .likeCount(likeCount)
                .createdAt(saved.getCreatedAt())
                .viewCount(saved.getViewCount())
                .commentCount(commentCount)
                .userNickname(user.getNickname())
                .userProfileImg(user.getProfilePicture())
                .build();
    }

    //게시물 수정
    @Transactional
    public PostDto updatePost(Long postId, Long userId, UpdatePostRequestDto req, MultipartFile imgFile) throws IOException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id=" + postId));
        if(!Objects.equals(post.getUser().getId(), userId)){
            throw new IllegalArgumentException("User is not the owner of the post");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));


        if (req.getTitle() != null) post.setTitle(req.getTitle());
        if (req.getText() != null) post.setText(req.getText());

        // 이미지 업로드
        String uploadPath = Paths.get(uploadDir).toAbsolutePath().toString();

        // 파일명 난수화
        String imgFileNameEncrypt = UUID.randomUUID() + "_" + StringUtils.cleanPath(imgFile.getOriginalFilename());
        File dest = new File(uploadPath, imgFileNameEncrypt);
        imgFile.transferTo(dest);
        String fileUrl = "/assets/img/data/" + imgFileNameEncrypt;
        Post updated = postRepository.save(post);

        long likeCount = postLikeRepository.countByPostId(updated.getId());
        long commentCount = commentRepository.countByPostId(updated.getId());

        return PostDto.builder()
                .id(updated.getId())
                .title(updated.getTitle())
                .text(updated.getText())
                .img(updated.getImg())
                .likeCount(likeCount)
                .createdAt(updated.getCreatedAt())
                .viewCount(updated.getViewCount())
                .commentCount(commentCount)
                .userNickname(user.getNickname())
                .userProfileImg(user.getProfilePicture())
                .build();
    }

    //게시물 삭제
    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id=" + postId));

        if(!Objects.equals(post.getUser().getId(), userId)){
            throw new IllegalArgumentException("User is not the owner of the post");
        }
        postRepository.deleteById(postId);
    }

}