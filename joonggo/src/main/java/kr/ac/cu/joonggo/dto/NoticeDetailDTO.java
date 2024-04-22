package kr.ac.cu.joonggo.dto;

import kr.ac.cu.joonggo.entity.NoticeCategoryEntity;
import kr.ac.cu.joonggo.entity.NoticeEntity;
import kr.ac.cu.joonggo.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDetailDTO {
    private Long id;
    private String title;
    private String content;
    private Long userId; // 공지사항을 작성한 사용자의 ID
    private String userName; // 유저 이름
    private int hit; // 조회수
    private Integer categoryId;  // 공지사항 카테고리 ID
    private String categoryName; // 카테고리 이름
    private LocalDateTime createdAt;
    private String imagePaths; // 이미지 URL들의 리스트(;으로 구분된 스트링)

    // DTO를 Entity로 변환
    public NoticeEntity toEntity(UserEntity user, NoticeCategoryEntity category) {
        return NoticeEntity.builder()
                .idx(this.id)
                .user(user)
                .category(category)
                .title(this.title)
                .imagePaths(this.imagePaths)
                .hit(0) // 새 공지사항일 경우 조회수는 0으로 초기화
                .content(this.content)
                .createdAt(this.createdAt != null ? this.createdAt : LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

}
