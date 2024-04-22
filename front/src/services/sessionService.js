import { jwtDecode } from 'jwt-decode';

const sessionService = {
    setUser: (user, token) => {
        sessionStorage.setItem('user', JSON.stringify(user));
        sessionStorage.setItem('token', token);
    },
    getUser: () => {
        const user = sessionStorage.getItem('user');
        return user ? JSON.parse(user) : null;
    },
    getToken: () => {
        return sessionStorage.getItem('token');
    },
    clearUser: () => {
        sessionStorage.removeItem('user');
        sessionStorage.removeItem('token');
    },
    isAuthenticated: () => {
        return !!sessionStorage.getItem('token') && !!sessionStorage.getItem('user');
    },
    isTokenExpired: () => {
        const token = sessionStorage.getItem('token');
        console.log("토큰:" + token + "만료체크");
        if (!token) return true;
        try {
            const decoded = jwtDecode(token);
            return decoded.exp * 1000 < Date.now();
        } catch (error) {
            return true;
        }
    }
};

export default sessionService;


// 이 서비스 사용예제
// 1. 로그인 후 사용자 정보 저장:
// sessionService.setUser({ username: 'user01', role: 'ROLE_USER' });

// 2. 사용자 정보 가져오기:
// const user = sessionService.getUser();
// console.log(user.username, user.role);

// 3. 로그아웃 시 사용자 정보 삭제:
// sessionService.clearUser();

// 4. 사용자 인증 상태 확인:
// const isAuthenticated = sessionService.isAuthenticated();
// console.log(isAuthenticated); // true 또는 false