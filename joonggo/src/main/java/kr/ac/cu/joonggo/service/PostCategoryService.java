package kr.ac.cu.joonggo.service;

import jakarta.persistence.EntityNotFoundException;
import kr.ac.cu.joonggo.entity.PostCategoryEntity;
import kr.ac.cu.joonggo.repository.PostCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostCategoryService {
    private final PostCategoryRepository postCategoryRepository;

    // 모든 게시물 카테고리 찾기
    public List<PostCategoryEntity> findAllCategories() {
        return postCategoryRepository.findAll();
    }

    // 아이디에 해당하는 게시물 카테고리 찾기
    public PostCategoryEntity findCategoryById(Integer id) {
        return postCategoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 공지 카테고리를 찾을 수 없습니다."));
    }

    // 게시물 카테고리 생성
    public PostCategoryEntity createCategory(PostCategoryEntity category) {
        return postCategoryRepository.save(category);
    }

    // 공지 카테고리 업데이트
    public PostCategoryEntity updateCategory(Integer id, PostCategoryEntity category) {
        // 기존 카테고리 정보 확인 및 업데이트 로직 구현
        return null;
    }

    // 공지 카테고리 삭제
    public void deleteCategory(Integer id) {
        postCategoryRepository.deleteById(id);
    }
}
