package com.bananackck.community_1._feature.post.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreatePostRequestDto {
    private String title;
    private String text;
}
