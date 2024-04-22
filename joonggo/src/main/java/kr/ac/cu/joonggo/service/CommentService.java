package kr.ac.cu.joonggo.service;

import jakarta.persistence.EntityNotFoundException;
import kr.ac.cu.joonggo.dto.CommentDTO;
import kr.ac.cu.joonggo.entity.CommentEntity;
import kr.ac.cu.joonggo.entity.PostEntity;
import kr.ac.cu.joonggo.entity.UserEntity;
import kr.ac.cu.joonggo.repository.CommentRepository;
import kr.ac.cu.joonggo.repository.PostRepository;
import kr.ac.cu.joonggo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 포스트의 댓글 목록 조회
    @Transactional(readOnly = true)
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        // 게시물 존재 여부 확인
        if (!postRepository.existsById(postId)) {
            throw new EntityNotFoundException("Post not found with id: " + postId);
        }
        return commentRepository.findCommentDTOsByPostId(postId);
    }

    // 댓글 추가 메서드
    @Transactional
    public CommentDTO addCommentToPost(Long postId, Long userId, String content) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        CommentEntity comment = CommentEntity.builder()
                .post(post)
                .user(user)
                .content(content)
                .build();

        CommentEntity savedComment = commentRepository.save(comment);
        return toDTO(savedComment);
    }

    // 댓글 수정 메서드
    @Transactional
    public CommentDTO updateComment(Long commentId, String content) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));

        comment.setContent(content);
        CommentEntity updatedComment = commentRepository.save(comment);
        return toDTO(updatedComment);
    }

    // 댓글 삭제 메서드
    @Transactional
    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new EntityNotFoundException("Comment not found with id: " + commentId);
        }
        commentRepository.deleteById(commentId);
    }

    // Entity를 DTO로 변환하는 메서드
    private CommentDTO toDTO(CommentEntity comment) {
        return new CommentDTO(
                comment.getIdx(),
                comment.getPost().getIdx(),
                comment.getUser().getIdx(),
                comment.getUser().getUsername(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }


}
