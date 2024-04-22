package kr.ac.cu.joonggo.repository;

import kr.ac.cu.joonggo.dto.LikeDTO;
import kr.ac.cu.joonggo.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    // 특정 사용자가 찜한 모든 게시물 목록 조회
    @Query("SELECT new kr.ac.cu.joonggo.dto.LikeDTO(l.idx, l.post.idx, l.user.idx, l.createdAt) " +
            "FROM LikeEntity l WHERE l.user.idx = :userId")
    List<LikeDTO> findAllLikesByUserId(@Param("userId") Long userId);

    // 특정 게시물을 찜한 모든 사용자 목록 조회
    @Query("SELECT new kr.ac.cu.joonggo.dto.LikeDTO(l.idx, l.post.idx, l.user.idx, l.createdAt) " +
            "FROM LikeEntity l WHERE l.post.idx = :postId")
    List<LikeDTO> findAllLikesByPostId(@Param("postId") Long postId);

    void deleteByPostIdx(Long postId);
}
