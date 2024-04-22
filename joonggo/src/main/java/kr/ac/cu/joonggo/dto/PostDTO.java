package kr.ac.cu.joonggo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDTO {
    private Long id;
    private String title;
    private Integer price;
    private String userName; // 유저 이름
    private int hit; // 조회수
    private String status; // 게시물 상태
    private String categoryName; // 카테고리 이름
    private LocalDateTime createdAt; // 생성 날짜
}
