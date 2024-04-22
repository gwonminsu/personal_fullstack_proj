package kr.ac.cu.joonggo.entity;

import jakarta.persistence.*;
import kr.ac.cu.joonggo.listener.DatePersistListener;
import kr.ac.cu.joonggo.listener.IAuditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Data
@Builder
@Entity
@EntityListeners(value = DatePersistListener.class)
@Table(name = "comment_t")
public class CommentEntity implements IAuditable { // 게시물 댓글 테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_t_idx")
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_t_idx")
    private UserEntity user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
