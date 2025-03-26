package com.bananackck.community_1._feature.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class userDto {
    private String nickname;
    private String email;
    private String img;
    private Long userId;
}
