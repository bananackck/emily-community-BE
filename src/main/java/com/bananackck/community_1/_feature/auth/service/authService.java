package com.bananackck.community_1._feature.auth.service;

import com.bananackck.community_1._feature.auth.dto.signupDto;
import com.bananackck.community_1._feature.user.User;
import com.bananackck.community_1._feature.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class authService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${file.upload-dir}")
    private String uploadDir;
    //회원가입
    public User signup(signupDto req, MultipartFile imgFile) throws IOException {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"EMAIL_CONFLICT");
        }
        if (userRepository.findByNickname(req.getNickname()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "NICKNAME_CONFLICT");
        }

        String fileUrl;
        if(imgFile.isEmpty()){
            fileUrl="assets/img/data/profile-basic.png";
        }
        else {
            // 이미지 업로드
            String uploadPath = Paths.get(uploadDir).toAbsolutePath().toString();

            // 파일명 난수화
            String imgFileNameEncrypt = UUID.randomUUID() + "_" + StringUtils.cleanPath(imgFile.getOriginalFilename());

            File dest = new File(uploadPath, imgFileNameEncrypt);
            imgFile.transferTo(dest);
            fileUrl = "/assets/img/data/" + imgFileNameEncrypt;
        }

        User user = User.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .nickname(req.getNickname())
                .profilePicture(fileUrl)
                .build();

        return userRepository.save(user);
    }

    public User loadUserById(Long id) throws UsernameNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + id));
    }
}
