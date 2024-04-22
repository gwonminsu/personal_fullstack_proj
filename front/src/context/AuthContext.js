import React, { createContext, useContext, useState, useEffect } from 'react';
import sessionService from '../services/sessionService';

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
    const [authData, setAuthData] = useState(sessionService.getUser());
    const [tokenExpiredAlertShown, setTokenExpiredAlertShown] = useState(false); // 경고 표시 여부를 추적하는 상태

    useEffect(() => {
        const validateToken = () => {
            if (sessionService.isTokenExpired()) {
                if (!tokenExpiredAlertShown) { // 만료된 토큰에 대해 경고를 아직 보여주지 않았다면
                    // alert('세션이 만료되었습니다. 다시 로그인해 주세요.');
                    sessionService.clearUser();
                    setAuthData(null);
                    setTokenExpiredAlertShown(true); // 경고를 보여줬음을 표시
                    console.log('토큰이 만료되어 로그아웃됨');
                }
            } else {
                console.log('토큰 유지 중..')
            }
        };

        validateToken();
        const intervalId = setInterval(validateToken, 1 * 60 * 1000); // 매 1분마다 토큰 유효성 검사

        return () => clearInterval(intervalId); // 컴포넌트 언마운트 시 인터벌 제거
    }, [tokenExpiredAlertShown]); // tokenExpiredAlertShown이 변경될 때마다 effect를 재실행

    return (
        <AuthContext.Provider value={{ authData, setAuthData }}>
            {children}
        </AuthContext.Provider>
    );
};
