package kr.ac.cu.joonggo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.ac.cu.joonggo.dto.LoginDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.*;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 처리를 위한 ObjectMapper 인스턴스


    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // 요청에서 JSON 데이터를 파싱
            LoginDTO loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);

            // 스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword(),
                    new ArrayList<>()
            );

            System.out.println(authToken);
            System.out.println(loginRequest.getPassword());

            // AuthenticationManager로 전달하여 사용자 인증 시도
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = customUserDetails.getRealUsername();
        Long userId = customUserDetails.getRealUserId();
        String email = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(email, role, 60*60*1000L);

        // 사용자 정보를 JSON 형태로 응답 본문에 추가
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("username", username);
        userInfo.put("userId", userId);
        userInfo.put("role", role);
        userInfo.put("token", token);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        System.out.println("successfulAuthentication이 정상적으로 호출됨");

        response.addHeader("Authorization", "Bearer " + token);

        try {
            // userInfo 맵을 JSON 문자열로 변환하여 응답에 쓰기
            response.getWriter().write(objectMapper.writeValueAsString(userInfo));
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        // 로그인 실패 시 에러 로그
        if (failed instanceof UsernameNotFoundException) {
            // 해당 사용자가 없을 때
            response.getWriter().write("해당 이메일에 해당하는 유저 없음");
            log.error("Login failed: 해당 이메일에 해당하는 유저 없음 " + request.getParameter("username"));
        } else if (failed instanceof BadCredentialsException) {
            // 비밀번호 불일치
            response.getWriter().write("비밀번호 불일치");
            log.error("Login failed: 해당 유저의 비밀번호 불일치 " + request.getParameter("username"));
        } else {
            // 기타 에러
            response.getWriter().write("인증 실패");
            log.error("Login failed: " + failed.getMessage());
        }

        // 로그인 실패시 401 응답 코드 반환
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
}