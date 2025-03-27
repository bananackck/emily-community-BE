package com.bananackck.community_1._feature.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


public class changePasswordDto {
    //TODO
    @Data
    public static class request{
//        private String currentPassword;
        private String newPassword;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class response{
        private String token;
    }
}
