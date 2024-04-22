package kr.ac.cu.joonggo.entity;

import kr.ac.cu.joonggo.repository.NoticeCategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoticeCategoryEntityTest {

    @Autowired
    NoticeCategoryRepository noticeCategoryRepository;

    @Test
    void createNoticeCategory() {
        NoticeCategoryEntity noticeCategory = noticeCategoryRepository.save(NoticeCategoryEntity.builder()
        .name("이벤트").build());
    }

}