package kr.ac.cu.joonggo.repository;

import kr.ac.cu.joonggo.dto.PostDTO;
import kr.ac.cu.joonggo.dto.PostDetailDTO;
import kr.ac.cu.joonggo.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    // id에 해당하는 게시물 하나 조회
    @Query("SELECT new kr.ac.cu.joonggo.dto.PostDTO(p.idx, p.title, p.price, u.username, p.hit, p.status, c.name, p.createdAt) FROM PostEntity p JOIN p.user u JOIN p.category c WHERE p.idx = :postId")
    PostDTO findPostByPostId(Long postId);

    // 전체 게시물 목록 조회
    @Query("SELECT new kr.ac.cu.joonggo.dto.PostDTO(p.idx, p.title, p.price, u.username, p.hit, p.status, c.name, p.createdAt) FROM PostEntity p JOIN p.user u JOIN p.category c order by p.createdAt DESC")
    List<PostDTO> findAllPostsAsDTO();

    // id에 해당하는 게시물 디테일 조회
    @Query("SELECT new kr.ac.cu.joonggo.dto.PostDetailDTO(p.idx, p.title, p.price, p.content, u.idx, u.username, p.hit, p.status, c.idx, c.name, p.createdAt, p.imagePaths) FROM PostEntity p JOIN p.user u JOIN p.category c WHERE p.idx = :id")
    Optional<PostDetailDTO> findPostDetailById(Long id);

    // 카테고리 id에 해당하는 게시물 목록 조회
    @Query("SELECT new kr.ac.cu.joonggo.dto.PostDTO(p.idx, p.title, p.price, u.username, p.hit, p.status, c.name, p.createdAt) FROM PostEntity p JOIN p.user u JOIN p.category c WHERE c.idx = :categoryId ORDER BY p.createdAt DESC")
    List<PostDTO> findAllByCategoryId(@Param("categoryId") Long categoryId);

    // 유저 id에 해당하는 게시물 목록 조회
    @Query("SELECT new kr.ac.cu.joonggo.dto.PostDTO(p.idx, p.title, p.price, u.username, p.hit, p.status, c.name, p.createdAt) FROM PostEntity p JOIN p.user u JOIN p.category c WHERE u.idx = :userId ORDER BY p.createdAt DESC")
    List<PostDTO> findAllByUserId(@Param("userId") Long userId);

    // 인기 게시물 순으로 정렬한 목록 조회
    @Query("SELECT new kr.ac.cu.joonggo.dto.PostDTO(p.idx, p.title, p.price, u.username, p.hit, p.status, c.name, p.createdAt) FROM PostEntity p JOIN p.user u JOIN p.category c WHERE p.status <> '거래 완료' ORDER BY p.hit DESC")
    List<PostDTO> findActivePostsSortedByHits();

    // 검색어에 따라 게시물을 검색하는 쿼리
    @Query("SELECT new kr.ac.cu.joonggo.dto.PostDTO(p.idx, p.title, p.price, u.username, p.hit, p.status, c.name, p.createdAt) FROM PostEntity p JOIN p.user u JOIN p.category c WHERE p.title LIKE %:query% OR p.content LIKE %:query%")
    List<PostDTO> searchByQuery(@Param("query") String query);

}
