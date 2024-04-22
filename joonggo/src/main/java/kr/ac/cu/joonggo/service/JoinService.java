package kr.ac.cu.joonggo.service;

import kr.ac.cu.joonggo.dto.JoinDTO;
import kr.ac.cu.joonggo.entity.UserEntity;
import kr.ac.cu.joonggo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO) {

        String username = joinDTO.getUsername();
        String email = joinDTO.getEmail();
        String password = joinDTO.getPassword();

        Boolean isExist = userRepository.existsByEmail(email);

        if (isExist) {

            return;
        }

        UserEntity data = new UserEntity();

        data.setUsername(username);
        data.setEmail(email);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_USER");

        System.out.println("username: " + data.getUsername() + ", email: " + data.getEmail() + ", password: " + data.getPassword());

        userRepository.save(data);
    }
}
