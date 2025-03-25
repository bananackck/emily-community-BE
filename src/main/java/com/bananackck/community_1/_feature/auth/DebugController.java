package com.bananackck.community_1._feature.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    @GetMapping("/auth")
    public ResponseEntity<Map<String, Object>> debugAuth(
            @AuthenticationPrincipal Jwt jwt) {

        return ResponseEntity.ok(Map.of(
                "principalType", jwt != null ? jwt.getClass().getSimpleName() : null,
                "subject", jwt != null ? jwt.getSubject() : null
        ));
    }
}
