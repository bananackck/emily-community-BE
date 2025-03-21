package com.bananackck.community_1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class LoginResponseDto {
    @Data
    @AllArgsConstructor
    public class LoginResponse {
        private String token;
    }

}
