package kr.ac.cu.joonggo.security;

import kr.ac.cu.joonggo.entity.UserEntity;
import kr.ac.cu.joonggo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //DB에서 조회
        UserEntity userData = userRepository.findByEmail(email);

        if (userData != null) {

            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
            return new CustomUserDetails(userData);
        }

        System.out.println("해당 이메일에 해당하는 사용자를 불러올 수 없음");
        return null;
    }
}
