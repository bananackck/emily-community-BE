package com.bananackck.community_1._feature.comment;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {
    private Long id;
    private String text;
    private LocalDateTime createdAt;
    private String userNickname;
    private String userProfileImg;

    public static CommentDto fromEntity(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .userNickname(comment.getUser() != null ? comment.getUser().getNickname() : null)
                .userProfileImg(comment.getUser() != null ? comment.getUser().getProfilePicture() : null)
                .build();
    }
}
