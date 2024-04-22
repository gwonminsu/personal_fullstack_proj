package kr.ac.cu.joonggo.service;

import jakarta.persistence.EntityNotFoundException;
import kr.ac.cu.joonggo.entity.UserEntity;
import kr.ac.cu.joonggo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // 모든 유저 찾기
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    // 아이디에 해당하는 유저 찾기
    public UserEntity findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 유저를 찾을 수 없습니다."));
    }

    // 유저 생성하기
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    // 유저 정보 업데이트
    public UserEntity updateUser(Long id, UserEntity user) {
        // 주어진 id로 사용자찾기
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        // 새로운 사용자 정보로 기존 사용자 정보를 업데이트
        userEntity.setUsername(user.getUsername());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());

        // 업데이트된 시간을 설정
        userEntity.setUpdatedAt(LocalDateTime.now());

        // 업데이트된 사용자를 저장하고 반환
        return userRepository.save(userEntity);
    }

    // 유저 삭제하기
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
