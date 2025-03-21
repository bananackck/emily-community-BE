package com.bananackck.community_1.dto;

import lombok.Data;

public class LoginRequestDto {
    @Data
    public class LoginRequest {
        private String email;
        private String password;
    }

}
