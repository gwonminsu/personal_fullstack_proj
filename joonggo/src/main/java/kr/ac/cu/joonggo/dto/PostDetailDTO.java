package kr.ac.cu.joonggo.dto;

import kr.ac.cu.joonggo.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailDTO {
    private Long id;
    private String title;
    private Integer price;
    private String content;
    private Long userId; // 공지사항을 작성한 사용자의 ID
    private String userName; // 유저 이름
    private int hit; // 조회수
    private String status; // 게시물 상태
    private Integer categoryId;  // 공지사항 카테고리 ID
    private String categoryName; // 카테고리 이름
    private LocalDateTime createdAt;
    private String imagePaths; // 이미지 URL들의 리스트(;으로 구분된 스트링)

    // DTO를 Entity로 변환
    public PostEntity toEntity(UserEntity user, PostCategoryEntity category) {
        return PostEntity.builder()
                .idx(this.id)
                .user(user)
                .category(category)
                .title(this.title)
                .price(this.price)
                .imagePaths(this.imagePaths)
                .hit(0) // 새 공지사항일 경우 조회수는 0으로 초기화
                .status(this.status)
                .content(this.content)
                .createdAt(this.createdAt != null ? this.createdAt : LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
