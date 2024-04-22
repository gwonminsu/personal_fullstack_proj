import axios from 'axios';
import { API_BASE_URL } from '../constant/constant';

// 전체 게시물 목록 가져오기
export const fetchPosts = async () => {

    try {
        const response = await axios.get(`${API_BASE_URL}/posts`); // 스프링 부트의 /posts 경로로 GET 요청을 보내기
        return response.data;
    } catch (error) {
        // 에러 처리
        console.error('게시물 불러오는 중 에러 발생', error);
        throw error;
    }
};

// 검색 쿼리에 따라 게시물을 검색하는 함수
export const fetchPostsBySearchQuery = async (query) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/posts/search`, {
            params: { query }
        });
        return response.data;
    } catch (error) {
        console.error('검색 쿼리로 게시물 로딩 에러', error);
        throw error;
    }
};

export const fetchPostOne = async (postId) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/posts/one/${postId}`);
        return response.data;
    } catch (error) {
        console.error('게시물 아이디의 게시물 로딩 에러', error);
        throw error;
    }
};



// 카테고리 id에 해당하는 게시물 목록 가져오기
export const fetchCategoryPosts = async (id) => {

    try {
        const response = await axios.get(`${API_BASE_URL}/posts/category/${id}`);
        return response.data;
    } catch (error) {
        // 에러 처리
        console.error('게시물 불러오는 중 에러 발생', error);
        throw error;
    }
};

// 유저 id에 해당하는 게시물 목록 가져오기
export const fetchUserPosts = async (id) => {

    try {
        const response = await axios.get(`${API_BASE_URL}/posts/user/${id}`);
        return response.data;
    } catch (error) {
        // 에러 처리
        console.error('게시물 불러오는 중 에러 발생', error);
        throw error;
    }
};

export const fetchHotPosts = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/posts/hot`);
        return response.data;
    } catch (error) {
        console.error('Hot 게시물 불러오는 중 에러 발생', error);
        throw error;
    }
};

export const fetchPostDetail = async (id) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/posts/${id}`);
        return response.data;
    } catch (error) {
        console.error('게시물 상세 정보 로딩 에러', error);
        throw error;
    }
};

// 카테고리 목록 가져오기
export const fetchCategories = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/posts/categories`);
        return response.data; // 이 배열을 컴포넌트에서 사용하여 옵션 목록을 렌더링
    } catch (error) {
        console.error('카테고리 로딩 중 에러 발생', error);
        throw error;
    }
};

// 파일을 포함한 게시물 추가
export const createPost = async (formData) => {
    try {
        const token = sessionStorage.getItem('token'); // 세션 스토리지에서 토큰 가져오기
        const response = await axios.post(`${API_BASE_URL}/posts`, formData, {
            headers: {
                // 'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
        });
        return response.data;
    } catch (error) {
        console.error('게시물 추가 중 에러 발생', error);
        throw error;
    }
};

// 파일을 포함한 게시물 수정
export const updatePost = async (id, formData) => {
    try {
        const token = sessionStorage.getItem('token'); // 세션 스토리지에서 토큰 가져오기
        const response = await axios.put(`${API_BASE_URL}/posts/${id}`, formData, {
            headers: {
                // 'Content-Type': 'multipart/form-data',
                'Authorization': `Bearer ${token}`
            },
        });
        return response.data;
    } catch (error) {
        console.error('게시물 수정 중 에러 발생', error);
        throw error;
    }
};

// 게시물 삭제
export const deletePost = async (id) => {
    try {
        const response = await axios.delete(`${API_BASE_URL}/posts/${id}`);
        return response.data;  // Optional: 처리 결과 반환
    } catch (error) {
        console.error('게시물 삭제 중 에러 발생', error);
        throw error;
    }
};