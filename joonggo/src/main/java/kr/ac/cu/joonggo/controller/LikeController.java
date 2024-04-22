package kr.ac.cu.joonggo.controller;

import kr.ac.cu.joonggo.dto.LikeDTO;
import kr.ac.cu.joonggo.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<LikeDTO>> getLikesByPostId(@PathVariable Long postId) {
        System.out.println("게시물 id로 찜 목록 불러오기 GET 요청..");
        return ResponseEntity.ok(likeService.getLikesByPostId(postId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LikeDTO>> getLikesByUserId(@PathVariable Long userId) {
        System.out.println("사용자 id로 찜 목록 불러오기 GET 요청..");
        return ResponseEntity.ok(likeService.getLikesByUserId(userId));
    }

    @PostMapping("/post/{postId}/user/{userId}")
    public ResponseEntity<LikeDTO> addLike(@PathVariable Long postId, @PathVariable Long userId) {
        System.out.println("찜 추가하기 POST 요청..");
        LikeDTO newLike = likeService.addLike(postId, userId);
        return ResponseEntity.ok(newLike);
    }

    @DeleteMapping("/{likeId}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long likeId) {
        System.out.println("찜 삭제하기 DELETE 요청..");
        likeService.removeLike(likeId);
        return ResponseEntity.ok().build();
    }
}
