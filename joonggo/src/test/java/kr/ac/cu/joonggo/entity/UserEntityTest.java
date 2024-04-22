package kr.ac.cu.joonggo.entity;

import kr.ac.cu.joonggo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserEntityTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void createUserTest() {
        userRepository.save(UserEntity.builder()
                .username("관리자")
                .email("kd0615@naver.com")
                .password("1234").build());
    }

    @Test
    void updateUserTest() {
        UserEntity user = userRepository.findByUsername("관리자");
        assertNotNull(user);
        user.setEmail("admin01@naver.com");
        userRepository.save(user);

        UserEntity updatedUser = userRepository.findByUsername("관리자");
        assertEquals("admin01@naver.com", updatedUser.getEmail());
    }

    @Test
    void deleteUserTest() {
        UserEntity user = userRepository.findByUsername("관리자");
        assertNotNull(user);
        userRepository.deleteById(user.getIdx());
    }
}