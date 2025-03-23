package com.bananackck.community_1._feature.post.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePostRequestDto {
    private String title;
    private String text;
    private String img;
}
