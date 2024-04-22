package kr.ac.cu.joonggo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Data
@Builder
@Entity
@Table(name = "notice_category_t")
public class NoticeCategoryEntity { // 공지 카테고리 테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column(length = 50, nullable = false)
    private String name;

}
