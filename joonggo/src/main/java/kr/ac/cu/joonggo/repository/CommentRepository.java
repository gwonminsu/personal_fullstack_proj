package kr.ac.cu.joonggo.repository;

import kr.ac.cu.joonggo.dto.CommentDTO;
import kr.ac.cu.joonggo.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Query("SELECT new kr.ac.cu.joonggo.dto.CommentDTO(c.idx, c.post.idx, c.user.idx, c.user.username, c.content, c.createdAt) " +
            "FROM CommentEntity c WHERE c.post.idx = :postId")
    List<CommentDTO> findCommentDTOsByPostId(@Param("postId") Long postId);

    void deleteByPostIdx(Long postId);
}
