package kr.ac.cu.joonggo.service;

import jakarta.persistence.EntityNotFoundException;
import kr.ac.cu.joonggo.dto.NoticeDTO;
import kr.ac.cu.joonggo.dto.NoticeDetailDTO;
import kr.ac.cu.joonggo.entity.NoticeCategoryEntity;
import kr.ac.cu.joonggo.entity.NoticeEntity;
import kr.ac.cu.joonggo.entity.UserEntity;
import kr.ac.cu.joonggo.repository.NoticeCategoryRepository;
import kr.ac.cu.joonggo.repository.NoticeRepository;
import kr.ac.cu.joonggo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    private final UserRepository userRepository;

    private final NoticeCategoryRepository noticeCategoryRepository;

    // 공지 페이지에서 모든 공지 찾기
    public List<NoticeDTO> findAllNotices() {
        return noticeRepository.findAllNoticesAsDTO();
    }

    // 아이디에 해당하는 공지 상세정보 가져오기
    public NoticeDetailDTO getNoticeDetail(Long id) {
        return noticeRepository.findNoticeDetailById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 공지를 가져올 수 없습니다."));
    }

    // 아이디에 해당하는 공지의 조회수 증가
    @Transactional
    public void incrementHit(Long id) {
        NoticeEntity notice = noticeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("공지사항이 존재하지 않습니다."));
        notice.setHit(notice.getHit() + 1);
        noticeRepository.save(notice); // 변경된 내용을 저장
    }

    // 공지 생성
    @Transactional
    public NoticeDetailDTO createNotice(NoticeDetailDTO noticeDTO) {
        NoticeCategoryEntity category = noticeCategoryRepository.findById(noticeDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        UserEntity user = userRepository.findById(noticeDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        NoticeEntity notice = NoticeEntity.builder()
                .title(noticeDTO.getTitle())
                .content(noticeDTO.getContent())
                .category(category)
                .user(user)
                .imagePaths(noticeDTO.getImagePaths())
                .build();

        NoticeEntity savedEntity = noticeRepository.save(notice);

        return convertToDetailDTO(savedEntity);
    }

    // 공지 업데이트
    @Transactional
    public NoticeDetailDTO updateNotice(Long id, NoticeDetailDTO noticeDTO) {
        NoticeEntity existingNotice = noticeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notice not found"));

        existingNotice.setTitle(noticeDTO.getTitle());
        existingNotice.setContent(noticeDTO.getContent());
        existingNotice.setImagePaths(noticeDTO.getImagePaths());
        // 추가적인 업데이트 필요한 필드들...

        if (noticeDTO.getCategoryId() != null) {
            NoticeCategoryEntity category = noticeCategoryRepository.findById(noticeDTO.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
            existingNotice.setCategory(category);
        }

        if (noticeDTO.getUserId() != null) {
            UserEntity user = userRepository.findById(noticeDTO.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            existingNotice.setUser(user);
        }

        NoticeEntity updatedEntity = noticeRepository.save(existingNotice);

        return convertToDetailDTO(updatedEntity);
    }

    // NoticeEntity를 NoticeDetailDTO로 변환
    private NoticeDetailDTO convertToDetailDTO(NoticeEntity notice) {
        return new NoticeDetailDTO(
                notice.getIdx(),
                notice.getTitle(),
                notice.getContent(),
                notice.getUser().getIdx(),
                notice.getUser().getUsername(), // or getUsername() based on your UserEntity
                notice.getHit(),
                notice.getCategory().getIdx(),
                notice.getCategory().getName(),
                notice.getCreatedAt(),
                notice.getImagePaths()
        );
    }

    // 공지 삭제
    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }
}
