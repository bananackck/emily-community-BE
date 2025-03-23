package com.bananackck.community_1._feature.post.entity;

import com.bananackck.community_1._feature.user.User;
import jakarta.persistence.*;

@Entity
@Table(name="post_like")
public class PostLike {

    @EmbeddedId
    private PostLikeId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @MapsId("postId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;
}

