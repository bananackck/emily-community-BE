package com.bananackck.community_1._feature.post.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostDto {
    private Long id;
    private String title;
    private String text;
    private String img;
    private LocalDateTime createdAt;
    private Long viewCount;

    private Long likeCount;

    private Long commentCount;

    private String userProfileImg; //연관된 User의 프사.
    private String userNickname; // 연관된 User의 닉네임
}
