package com.bananackck.community_1._feature.comment;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

public class CommentDto {
    @Data
    @Builder
    public static class ViewCommentDto {
        private Long id;
        private String text;
        private LocalDateTime createdAt;
        private Long userId;
        private String userNickname;
        private String userProfileImg;
    }

    public static ViewCommentDto fromEntity(Comment comment) {
        return ViewCommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .userId(comment.getUser() != null ? comment.getUser().getId() : null)
                .userNickname(comment.getUser() != null ? comment.getUser().getNickname() : null)
                .userProfileImg(comment.getUser() != null ? comment.getUser().getProfilePicture() : null)
                .build();
    }

    @Data
    @Builder
    public static class CreateCommentDto {
        private String text;
    }
    @Data
    @Builder
    public static class UpdateCommentDto {
        private String text;
    }
}

