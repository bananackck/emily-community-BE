package com.bananackck.community_1._feature.user;

import com.bananackck.community_1._feature.user.dto.changePasswordDto;
import com.bananackck.community_1._feature.user.dto.userDto;
import com.bananackck.community_1._feature.user.dto.userUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final userService userService;

    //프로필 변경(닉네임, 프사)
    @PatchMapping(value="/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
    public ResponseEntity<userDto> updateProfile(
            @RequestPart("data") userUpdateDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal Jwt jwt) throws IOException {

        log.info("hello");
        long userId = Long.parseLong(jwt.getSubject());  // 토큰 sub → userId
        userDto updated = userService.updateProfile(userId, dto, file);
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

    //회원탈퇴
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteAccount(@AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.valueOf(jwt.getSubject());
        userService.deleteAccount(userId);
        return ResponseEntity.noContent().build();
    }


}
