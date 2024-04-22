import axios from 'axios';
import { API_BASE_URL } from '../constant/constant';

const token = sessionStorage.getItem('token');

// 사용자가 찜한 게시물 id들 가져오기
export const fetchLikePostIds = async (userId) => {

    try {
        const response = await axios.get(`${API_BASE_URL}/likes/user/${userId}`);
        return response.data;
    } catch (error) {
        console.error('찜한 게시물 id들 불러오는 중 에러 발생', error);
        throw error;
    }
};

// 해당 게시물을 찜한 사용자들의 id들 가져오기
export const fetchLikeUserIds = async (postId) => {

    try {
        const response = await axios.get(`${API_BASE_URL}/likes/post/${postId}`);
        return response.data;
    } catch (error) {
        console.error('게시물을 찜한 사용자 id들 불러오는 중 에러 발생', error);
        throw error;
    }
};

// 사용자가 게시물 찜
export const addLike = async (postId, userId) => {
    try {
        const response = await axios.post(`${API_BASE_URL}/likes/post/${postId}/user/${userId}`, {}, {
            headers: {
                'Authorization': `Bearer ${token}`
            },
        });
        return response.data;
    } catch (error) {
        console.error('찜 추가 중 에러 발생', error);
        throw error;
    }
};

// 찜 취소(삭제)
export const deleteLike = async (likeId) => {
    try {
        const response = await axios.delete(`${API_BASE_URL}/likes/${likeId}`);
        return response.data;
    } catch (error) {
        console.error('찜 삭제 중 에러 발생', error);
        throw error;
    }
};

// 게시물의 찜 개수 계산
export const fetchLikeCount = async (postId) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/likes/post/${postId}`);
        return response.data.length;
    } catch (error) {
        console.error('게시물의 찜 개수 가져오는 중 에러 발생', error);
        throw error;
    }
};