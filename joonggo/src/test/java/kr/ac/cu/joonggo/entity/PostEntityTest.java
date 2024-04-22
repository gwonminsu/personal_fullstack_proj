package kr.ac.cu.joonggo.entity;

import kr.ac.cu.joonggo.repository.PostCategoryRepository;
import kr.ac.cu.joonggo.repository.PostRepository;
import kr.ac.cu.joonggo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostEntityTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostCategoryRepository postCategoryRepository;

    @Test
    void createPostCategory() {
        postCategoryRepository.save(PostCategoryEntity.builder()
                .name("디지털 기기").build());
        postCategoryRepository.save(PostCategoryEntity.builder()
                .name("가구/인테리어").build());
        postCategoryRepository.save(PostCategoryEntity.builder()
                .name("생활 가전").build());
        postCategoryRepository.save(PostCategoryEntity.builder()
                .name("스포츠/레저").build());
        postCategoryRepository.save(PostCategoryEntity.builder()
                .name("취미/게임/음반").build());
        postCategoryRepository.save(PostCategoryEntity.builder()
                .name("식품").build());
    }

    @Test
    void createPost() {
        UserEntity user1 = userRepository.findByUsername("유저1");
        UserEntity user2 = userRepository.findByUsername("유저2");

        postRepository.save(PostEntity.builder()
                .title("디지털 기기1")
                .content("테스트")
                .user(user1)
                .price(50000)
                .status("판매 중")
                .category(postCategoryRepository.findByName("디지털 기기")).build());

        postRepository.save(PostEntity.builder()
                .title("가구1")
                .content("테스트")
                .price(60000)
                .status("예약 중")
                .user(user1)
                .category(postCategoryRepository.findByName("가구/인테리어")).build());

        postRepository.save(PostEntity.builder()
                .title("디지털 기기2")
                .content("테스트")
                .price(30000)
                .status("거래 완료")
                .user(user2)
                .category(postCategoryRepository.findByName("디지털 기기")).build());
    }

}