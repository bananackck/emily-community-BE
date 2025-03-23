package com.bananackck.community_1._feature.post.dto;

import lombok.Data;

@Data
public class CreatePostRequestDto {
    private String title;
    private String text;
    private String img;
}
