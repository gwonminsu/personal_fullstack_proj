import axios from 'axios';
import { API_BASE_URL } from '../constant/constant';

const token = sessionStorage.getItem('token');

// 게시물에 해당하는 댓글 가져오기
export const fetchComments = async (id) => {

    try {
        const response = await axios.get(`${API_BASE_URL}/comments/post/${id}`);
        return response.data;
    } catch (error) {
        console.error('댓글 불러오는 중 에러 발생', error);
        throw error;
    }
};

// 게시물에 댓글 추가
export const addComment = async (postId, userId, content) => {
    try {
        const response = await axios.post(`${API_BASE_URL}/comments/post/${postId}/user/${userId}`, { content }, {
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
        });
        return response.data;
    } catch (error) {
        console.error('댓글 추가 중 에러 발생', error);
        throw error;
    }
};

// 댓글 수정
export const updateComment = async (commentId, content) => {
    try {
        const response = await axios.put(`${API_BASE_URL}/comments/${commentId}`, { content }, {
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
        });
        return response.data;
    } catch (error) {
        console.error('댓글 수정 중 에러 발생', error);
        throw error;
    }
};

// 게시물에 달려있는 댓글 삭제
export const deleteComment = async (id) => {
    try {
        const response = await axios.delete(`${API_BASE_URL}/comments/${id}`);
        return response.data;
    } catch (error) {
        console.error('댓글 삭제 중 에러 발생', error);
        throw error;
    }
};

// 게시물의 댓글 개수 계산
export const fetchCommentCount = async (postId) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/comments/post/${postId}`);
        return response.data.length;
    } catch (error) {
        console.error('댓글 개수 가져오는 중 에러 발생', error);
        throw error;
    }
};