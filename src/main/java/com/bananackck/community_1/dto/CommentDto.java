package com.bananackck.community_1.dto;

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
    // 필요한 다른 필드들
}
