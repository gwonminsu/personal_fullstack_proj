import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function JoinPage() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [username, setUsername] = useState('');
    const navigate = useNavigate();

    const handleJoin = async () => {
        try {
            await axios.post('http://localhost:7979/join', { email, password, username });
            alert('가입 성공 로그인 페이지로 이동합니다!');
            navigate('/login');
        } catch (error) {
            alert('회원가입 실패')
            console.error('Registration error:', error);
        }
    };

    return (
        <div className="flex items-center justify-center min-h-screen bg-gray-100">
            <div className="w-full max-w-md">
                <form className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 w-full max-w-md flex flex-col justify-center min-h-[450px]">
                    <h1 className="text-3xl font-bold mb-4 flex items-center justify-center text-amber-500 pb-6">회원가입</h1>
                    <div className="mb-4">
                        <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="username">
                            사용자 이름
                        </label>
                        <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" id="username" type="text" placeholder="사용자 이름을 입력하세요" value={username} onChange={(e) => setUsername(e.target.value)} />
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="email">
                            이메일
                        </label>
                        <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" id="email" type="email" placeholder="이메일을 입력하세요" value={email} onChange={(e) => setEmail(e.target.value)} />
                    </div>
                    <div className="mb-6">
                        <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="password">
                            비밀번호
                        </label>
                        <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline" id="password" type="password" placeholder="비밀번호를 입력하세요" value={password} onChange={(e) => setPassword(e.target.value)} />
                    </div>
                    <div className="flex items-center justify-between pt-5">
                        <button className="bg-amber-500 hover:bg-amber-600 text-white font-bold py-2 px-4 w-full rounded focus:outline-none focus:shadow-outline" type="button" onClick={handleJoin}>
                            가입하기
                        </button>
                    </div>
                </form>
                <p className="text-center text-gray-500 text-xs">
                    &copy;2024 Minsu Corp. All rights reserved.
                </p>
            </div>
        </div>
    );
}

export default JoinPage;
