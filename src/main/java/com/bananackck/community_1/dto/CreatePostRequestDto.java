package com.bananackck.community_1.dto;

import lombok.Data;

@Data
public class CreatePostRequestDto {
    private String title;
    private String text;
    private Long userId;
}
