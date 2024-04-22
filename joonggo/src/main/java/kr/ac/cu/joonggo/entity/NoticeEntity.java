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
@Table(name = "notice_t")
public class NoticeEntity implements IAuditable { // 공지 컨텐츠 테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // 얘를 쿼리하기 전까지는 기다리게 함 (불필요한 쿼리 방지)
    @JoinColumn(name = "user_t_idx")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "notice_category_t_idx")
    private NoticeCategoryEntity category;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String imagePaths; // "path1;path2;path3" <- 이런식으로 이미지 경로 저장

    @Column(name = "hit", nullable = false)
    private int hit = 0;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
