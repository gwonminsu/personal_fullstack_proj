package kr.ac.cu.joonggo.controller;

import kr.ac.cu.joonggo.dto.JoinDTO;
import kr.ac.cu.joonggo.service.JoinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/join")
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }

    @PostMapping
    public ResponseEntity<String> joinProcess(@RequestBody JoinDTO joinDTO) {
        try {
            joinService.joinProcess(joinDTO);
            return ResponseEntity.ok("회원가입 성공");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("회원가입 실패: " + e.getMessage());
        }
    }
}
