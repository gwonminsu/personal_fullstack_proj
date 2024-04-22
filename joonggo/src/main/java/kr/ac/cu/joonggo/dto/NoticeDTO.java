package kr.ac.cu.joonggo.dto;

import kr.ac.cu.joonggo.entity.NoticeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDTO {
    private Long id;
    private String title;
    private String userName; // 유저 이름
    private int hit; // 조회수
    private String categoryName; // 카테고리 이름
    private LocalDateTime createdAt; // 생성 날짜

}
