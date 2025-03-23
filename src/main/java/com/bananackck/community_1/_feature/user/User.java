package com.bananackck.community_1._feature.user;

import com.bananackck.community_1._feature.comment.Comment;
import com.bananackck.community_1._feature.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter @Setter
@AllArgsConstructor
@Entity
@Builder
@Table(name="users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include @ToString.Include
    private Long id;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String password;

    @Column(nullable=false, unique=true)
    private String nickname;

    private String profilePicture;

    @OneToMany(mappedBy="user", cascade=CascadeType.ALL)
    private Set<Post> posts;

    @OneToMany(mappedBy="user", cascade=CascadeType.ALL)
    private Set<Comment> comments;

    public User(){

    }
}
