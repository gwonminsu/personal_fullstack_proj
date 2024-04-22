package kr.ac.cu.joonggo.dto;

import lombok.*;

@Getter
@Setter
public class JoinDTO {
    private String username; // 사용자의 이름
    private String email; // 사용자의 이메일
    private String password; // 사용자의 비밀번호
}
