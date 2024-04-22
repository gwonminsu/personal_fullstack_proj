package kr.ac.cu.joonggo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDTO {
    private Long id;
    private Long postId;
    private Long userId;
    private String userName;
    private String content;
    private LocalDateTime createdAt;
}
