package com.bananackck.community_1._feature.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class userUpdateDto {
    private String nickname;
}
