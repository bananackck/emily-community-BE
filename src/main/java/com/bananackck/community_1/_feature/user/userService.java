package com.bananackck.community_1._feature.user;

import com.bananackck.community_1._feature.post.entity.Post;
import com.bananackck.community_1._feature.user.dto.changePasswordDto;
import com.bananackck.community_1._feature.user.dto.userDto;
import com.bananackck.community_1._feature.user.dto.userUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class userService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${file.upload-dir}")
    private String uploadDir;

    //닉네임과 프로필 사진 변
    @Transactional
    public userDto updateProfile(long userId, userUpdateDto req, MultipartFile imgFile) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        if(req.getNickname()!=null) user.setNickname(req.getNickname());
        if(imgFile!=null){
            // 이미지 업로드
            String uploadPath = Paths.get(uploadDir).toAbsolutePath().toString();

            // 파일명 난수화
            String imgFileNameEncrypt = UUID.randomUUID() + "_" + StringUtils.cleanPath(imgFile.getOriginalFilename());
            File dest = new File(uploadPath, imgFileNameEncrypt);
            imgFile.transferTo(dest);
            String fileUrl = "/assets/img/data/" + imgFileNameEncrypt;
            user.setProfilePicture(fileUrl);

            log.info("🔎fileUrl={}", fileUrl);
        }

        User updated = userRepository.save(user);

        return userDto.builder()
                .nickname(updated.getNickname())
                .img(updated.getProfilePicture())
                .email(updated.getEmail())
                .userId(updated.getId())
                .build();
    }

    //비밀번호 변경
    @Transactional
    public changePasswordDto.response changePassword(Long userId, changePasswordDto.request dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

//        //현재 비밀번호 맞는지 확인
//        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
//            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
//        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        User updated = userRepository.save(user);
        log.info("🔎updated={}", updated.getPassword());
        return changePasswordDto.response.builder()
                .token(updated.getPassword())
                .build();
    }

    //회원탈퇴
    @Transactional
    public void deleteAccount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.delete(user);
    }

}
