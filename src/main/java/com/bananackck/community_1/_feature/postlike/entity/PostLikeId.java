package com.bananackck.community_1._feature.postlike.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PostLikeId implements Serializable {
    private Long userId;
    private Long postId;
}
