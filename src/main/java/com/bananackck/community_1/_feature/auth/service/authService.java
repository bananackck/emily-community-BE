package com.bananackck.community_1._feature.auth.service;

import com.bananackck.community_1._feature.auth.dto.signupDto;
import com.bananackck.community_1._feature.user.User;
import com.bananackck.community_1._feature.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class authService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signup(signupDto req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"EMAIL_CONFLICT");
        }
        if (userRepository.findByNickname(req.getNickname()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "NICKNAME_CONFLICT");
        }

        User user = User.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .nickname(req.getNickname())
                .profilePicture(req.getImg())
                .build();

        return userRepository.save(user);
    }
}
