package com.bananackck.community_1._feature.auth;

import com.bananackck.community_1._feature.auth.dto.*;
import com.bananackck.community_1._feature.auth.service.authService;
import com.bananackck.community_1._feature.user.User;
import com.bananackck.community_1.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final authService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginDto.LoginResponse> login(@RequestBody LoginDto.LoginRequest req) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        String token = jwtUtil.generateToken(principal.getId().toString());

        return ResponseEntity.ok(
                LoginDto.LoginResponse.builder()
                        .token(token)
                        .build()
        );
    }


    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody signupDto req) {
        User saved = authService.signup(req);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("message", "회원가입 성공", "userId", saved.getId().toString()));

    }
}
