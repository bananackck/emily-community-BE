package com.bananackck.community_1._feature.postlike.entity;

import com.bananackck.community_1._feature.post.entity.Post;
import com.bananackck.community_1._feature.user.User;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(
        name = "post_like",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","post_id"})
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id", nullable=false)
    private Post post;
}
