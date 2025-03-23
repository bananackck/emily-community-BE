package com.bananackck.community_1._feature.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class LoginDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequest {
        private String email;
        private String password;
    }
    @Data
    @Builder
    @AllArgsConstructor
    public static class LoginResponse {
        private String token;
    }
}
