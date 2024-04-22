import axios from 'axios';
import { API_BASE_URL } from '../constant/constant';

export const fetchNotices = async () => {

    try {
        const response = await axios.get(`${API_BASE_URL}/notices`); // 스프링 부트의 /notices 경로로 GET 요청을 보내기
        return response.data;
    } catch (error) {
        // 에러 처리
        console.error('공지 불러오는 중 에러 발생', error);
        // console.log("환경변수: "+process.env.REACT_APP_API_BASE_URL);
        throw error;
    }
};

export const fetchNoticeDetail = async (id) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/notices/${id}`);
        return response.data;
    } catch (error) {
        console.error('공지사항 상세 정보 로딩 에러', error);
        throw error;
    }
};

// 카테고리 목록 가져오기
export const fetchCategories = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/notices/categories`);
        return response.data; // 이 배열을 컴포넌트에서 사용하여 옵션 목록을 렌더링
    } catch (error) {
        console.error('카테고리 로딩 중 에러 발생', error);
        throw error;
    }
};

// 파일을 포함한 공지사항 추가
export const createNotice = async (formData) => {
    try {
        const token = sessionStorage.getItem('token'); // 세션 스토리지에서 토큰 가져오기
        const response = await axios.post(`${API_BASE_URL}/notices`, formData, {
            headers: {
                // 'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
        });
        return response.data;
    } catch (error) {
        console.error('공지사항 추가 중 에러 발생', error);
        throw error;
    }
};

// 파일을 포함한 공지사항 수정
export const updateNotice = async (id, formData) => {
    try {
        const token = sessionStorage.getItem('token'); // 세션 스토리지에서 토큰 가져오기
        const response = await axios.put(`${API_BASE_URL}/notices/${id}`, formData, {
            headers: {
                // 'Content-Type': 'multipart/form-data',
                'Authorization': `Bearer ${token}`
            },
        });
        return response.data;
    } catch (error) {
        console.error('공지사항 수정 중 에러 발생', error);
        throw error;
    }
};

export const deleteNotice = async (id) => {
    try {
        const response = await axios.delete(`${API_BASE_URL}/notices/${id}`);
        return response.data;  // Optional: 처리 결과 반환
    } catch (error) {
        console.error('공지사항 삭제 중 에러 발생', error);
        throw error;
    }
};