package com.bananackck.community_1._feature.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class userUpdateDto {
    private String nickname;
    private String img;
}
