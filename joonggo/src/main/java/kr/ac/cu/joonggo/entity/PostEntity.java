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
@Table(name = "post_t")
public class PostEntity implements IAuditable { // 게시물 테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_t_idx")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_category_t_idx")
    private PostCategoryEntity category;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String imagePaths; // 이미지 경로를 저장

    @Column(nullable = false)
    private int hit = 0;

    @Column(nullable = false) // 가격
    private int price = 0;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder.Default
    @Column(nullable = false)
    private String status = "판매 중"; // 판매 중, 예약 중, 거래 완료

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
