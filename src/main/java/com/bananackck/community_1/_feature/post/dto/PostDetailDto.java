package com.bananackck.community_1._feature.post.dto;

import com.bananackck.community_1._feature.comment.CommentDto;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PostDetailDto {
    private Long id;
    private String title;
    private String text;
    private String img;
    private LocalDateTime createdAt;
    private Long viewCount;

    private Long likeCount;
    private boolean isLiked;

    private Long commentCount;

    private Long userId;
    private String userNickname;
    private String userProfileImg;
    private List<CommentDto.ViewCommentDto> comments;
}
