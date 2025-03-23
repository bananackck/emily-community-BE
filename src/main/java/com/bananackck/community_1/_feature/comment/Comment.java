package com.bananackck.community_1._feature.comment;

import com.bananackck.community_1._feature.post.entity.Post;
import com.bananackck.community_1._feature.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@AllArgsConstructor
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(columnDefinition="TEXT", nullable=false)
    private String text;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Comment() {

    }

    // getters/setters
}
