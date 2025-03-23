package com.bananackck.community_1._feature.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class userService {
    private final UserRepository userRepository;

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
}
