package com.bananackck.community_1._feature.user;

import com.bananackck.community_1._feature.user.dto.changePasswordDto;
import com.bananackck.community_1._feature.user.dto.userUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;


import jakarta.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class userService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public userUpdateDto updateProfile(long userId, userUpdateDto req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        if(req.getNickname()!=null) user.setNickname(req.getNickname());
        if(req.getImg()!=null) user.setProfilePicture(req.getImg());

        User updated = userRepository.save(user);

        return userUpdateDto.builder()
                .nickname(updated.getNickname())
                .img(updated.getProfilePicture())
                .build();
    }
    @Transactional
    public void changePassword(Long userId, changePasswordDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        //TODO
//        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
//            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
//        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
    }

    @Transactional
    public void deleteAccount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.delete(user);
    }

}
