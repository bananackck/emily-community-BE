package com.bananackck.community_1._feature.post.entity;

import com.bananackck.community_1._feature.comment.Comment;
import com.bananackck.community_1._feature.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Builder
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@ToString(onlyExplicitlyIncluded = true)
@ToString(exclude = {"user"})
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
//    @ToString.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Builder.Default
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private String img;


    @Builder.Default
    @Column(nullable = false)
    private Long viewCount = 0L;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostLike> likes;

    public Post() {

    }
}