package kr.ac.cu.joonggo.entity;

import jakarta.persistence.*;
import kr.ac.cu.joonggo.listener.DatePersistListener;
import kr.ac.cu.joonggo.listener.IAuditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Data
@Builder
@Entity
@EntityListeners(value = DatePersistListener.class)
@Table(name = "user_t")
public class UserEntity implements IAuditable { // 유저 테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "USER"; // 추가

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
