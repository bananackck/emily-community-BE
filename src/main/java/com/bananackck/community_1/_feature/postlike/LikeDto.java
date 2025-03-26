package com.bananackck.community_1._feature.postlike;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LikeDto {
    private Long likeCount;
    private Long postId;
    private Long userId;
}
