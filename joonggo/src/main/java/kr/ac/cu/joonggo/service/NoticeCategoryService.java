package kr.ac.cu.joonggo.service;

import jakarta.persistence.EntityNotFoundException;
import kr.ac.cu.joonggo.entity.NoticeCategoryEntity;
import kr.ac.cu.joonggo.repository.NoticeCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeCategoryService {
    private final NoticeCategoryRepository noticeCategoryRepository;

    // 모든 공지 카테고리 찾기
    public List<NoticeCategoryEntity> findAllCategories() {
        return noticeCategoryRepository.findAll();
    }

    // 아이디에 해당하는 공지 카테고리 찾기
    public NoticeCategoryEntity findCategoryById(Integer id) {
        return noticeCategoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 공지 카테고리를 찾을 수 없습니다."));
    }

    // 공지 카테고리 생성
    public NoticeCategoryEntity createCategory(NoticeCategoryEntity category) {
        return noticeCategoryRepository.save(category);
    }

    // 공지 카테고리 업데이트
    public NoticeCategoryEntity updateCategory(Integer id, NoticeCategoryEntity category) {
        // 기존 카테고리 정보 확인 및 업데이트 로직 구현
        return null;
    }

    // 공지 카테고리 삭제
    public void deleteCategory(Integer id) {
        noticeCategoryRepository.deleteById(id);
    }
}
