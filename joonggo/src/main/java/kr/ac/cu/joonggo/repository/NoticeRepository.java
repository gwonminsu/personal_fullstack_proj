package kr.ac.cu.joonggo.repository;

import kr.ac.cu.joonggo.dto.NoticeDTO;
import kr.ac.cu.joonggo.dto.NoticeDetailDTO;
import kr.ac.cu.joonggo.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

    @Query("SELECT new kr.ac.cu.joonggo.dto.NoticeDTO(n.idx, n.title, u.username, n.hit, c.name, n.createdAt) FROM NoticeEntity n JOIN n.user u JOIN n.category c order by n.createdAt DESC ")
    List<NoticeDTO> findAllNoticesAsDTO();


    @Query("SELECT new kr.ac.cu.joonggo.dto.NoticeDetailDTO(n.idx, n.title, n.content, u.idx, u.username, n.hit, c.idx, c.name, n.createdAt, n.imagePaths) FROM NoticeEntity n JOIN n.user u JOIN n.category c WHERE n.idx = :id")
    Optional<NoticeDetailDTO> findNoticeDetailById(Long id);
}
