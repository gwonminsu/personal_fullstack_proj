package kr.ac.cu.joonggo.controller;

import kr.ac.cu.joonggo.dto.CommentDTO;
import kr.ac.cu.joonggo.entity.CommentEntity;
import kr.ac.cu.joonggo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    // 특정 포스트의 모든 댓글 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable Long postId) {
        System.out.println("게시물 댓글 목록 GET 요청..");
        List<CommentDTO> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    // 댓글 추가
    @PostMapping("/post/{postId}/user/{userId}")
    public ResponseEntity<CommentDTO> addComment(
            @PathVariable Long postId,
            @PathVariable Long userId,
            @RequestBody CommentDTO commentDTO) {
        System.out.println("게시물 댓글 추가 POST 요청..");
        CommentDTO newComment = commentService.addCommentToPost(postId, userId, commentDTO.getContent());
        return ResponseEntity.ok(newComment);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentDTO commentDTO) {
        System.out.println("게시물 댓글 수정 PUT 요청..");
        CommentDTO updatedComment = commentService.updateComment(commentId, commentDTO.getContent());
        return ResponseEntity.ok(updatedComment);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        System.out.println("게시물 댓글 삭제 DELETE 요청..");
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

}
