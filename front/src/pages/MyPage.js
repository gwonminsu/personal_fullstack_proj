import React, { useEffect, useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import sessionService from '../services/sessionService';

function PostPage() {
    const navigate = useNavigate();
    const user = sessionService.getUser();

    useEffect(() => {
        document.body.classList.add('bg-slate-200');

        return () => {
            document.body.classList.remove('bg-slate-200');
        };
    }, []);

    const handlePostCreate = () => {
        navigate('/post-form');
    };

    // 사용자가 로그인되어 있지 않으면 로그인 페이지로 리다이렉트
    if (!user) {
        navigate('/login');
        return <></>;
    }

    return (
        <div className="container mx-auto p-4">
            <div className="p-6 rounded-lg shadow-lg bg-amber-100 min-h-screen">
                <div className="p-6">
                    <div className="font-bold text-3xl mb-2">MY 연근</div>
                    <div className=" left-0 w-11/12 mb-8 border-t border-gray-800"></div>
                    <div className="flex items-center justify-between mb-5">
                        <span className="flex items-center text-sm font-medium">
                            <img src="/images/user_ico.png" alt="User" className="h-8 w-8 mr-2" />
                            {user.username}
                        </span>
                        <button
                            className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-700"
                            onClick={handlePostCreate}
                        >
                            중고 물품 판매하기
                        </button>
                    </div>
                    <button
                        className="px-4 py-2 bg-gray-200 rounded hover:bg-gray-300"
                        onClick={() => navigate('/post')}
                    >
                        전체 글 보기
                    </button>
                    
                </div>
                <div className="px-6 py-4">
                    <div className="font-bold text-xl mb-2">내 거래</div>
                    <div className="flex flex-col">
                        <Link to={`/post/user/${user.userId}`} className="mb-2 text-blue-700 hover:underline">📄 판매 목록</Link>
                        <Link to={`/post/like/${user.userId}`} className="mb-2 text-blue-700 hover:underline">❤️ 찜 목록</Link>
                        <Link to="/my-messages" className="text-blue-700 hover:underline">💬 내 채팅</Link>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default PostPage;
