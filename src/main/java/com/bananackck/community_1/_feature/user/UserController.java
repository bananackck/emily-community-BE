package com.bananackck.community_1._feature.user;

import com.bananackck.community_1._feature.user.dto.changePasswordDto;
import com.bananackck.community_1._feature.user.dto.userUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final userService userService;

    //프로필 변경(닉네임, 프사)
    @PatchMapping("/me")
    public ResponseEntity<userUpdateDto> updateProfile(
            @RequestBody userUpdateDto dto,
            @AuthenticationPrincipal Jwt jwt) {

        long userId = Long.parseLong(jwt.getSubject());  // 토큰 sub → userId
        userUpdateDto updated = userService.updateProfile(userId, dto);
        return ResponseEntity.ok(updated);
    }

    //비밀번호 변경
    @PatchMapping("/me/password")
    public ResponseEntity<Void> updatePassword(
            @RequestBody changePasswordDto dto,
            @AuthenticationPrincipal Jwt jwt) {

        long userId = Long.parseLong(jwt.getSubject());  // 토큰 sub → userId
        userService.changePassword(userId, dto);
        return ResponseEntity.ok().build();
    }

}
