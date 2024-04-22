import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import sessionService from '../services/sessionService';

function LoginPage() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        document.body.classList.add('bg-white-200');

        return () => {
            document.body.classList.remove('bg-white-200');
          };
    }, []);

    const handleLogin = async () => {
        try {
            const response = await axios.post('http://localhost:7979/login', { email, password });
            const { username, userId, role, token } = response.data;
            console.dir(response.data);
            console.log(username, userId);
            console.log(role);
            sessionService.setUser({ username, userId, role }, token );
            alert(`로그인에 성공했습니다. ${role === 'ROLE_USER' ? '사용자' : '관리자'} ${username}님 안녕하세요!`);
            navigate('/');
        } catch (error) {
            console.error('Login error:', error);
            alert('로그인 실패');
        }
    };

    return (
        <div className="flex items-center justify-center min-h-screen bg-gray-100">
            <div className="w-full max-w-md">
                <form className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 w-full max-w-md flex flex-col justify-center min-h-[450px]">
                <h1 className="text-3xl font-bold mb-4 flex items-center justify-center text-amber-500 pb-6">로그인</h1>
                    <div className="mb-4">
                        <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="email">
                            아이디(이메일 형식)
                        </label>
                        <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" id="email" type="email" placeholder="아이디를 입력하세요" value={email} onChange={(e) => setEmail(e.target.value)} />
                    </div>
                    <div className="mb-6">
                        <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="password">
                            비밀번호
                        </label>
                        <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline" id="password" type="password" placeholder="비밀번호를 입력하세요" value={password} onChange={(e) => setPassword(e.target.value)} />
                    </div>
                    <div className="flex items-center justify-between pt-5">
                        <button className="bg-amber-500 hover:bg-amber-600 text-white font-bold py-2 px-4 w-full rounded focus:outline-none focus:shadow-outline" type="button" onClick={handleLogin}>
                            로그인
                        </button>
                    </div>
                    <div className="text-sm pt-3 flex items-center justify-center">아직 회원정보가 없나요? <div className=" text-orange-500 no-underline hover:underline pl-2"><a href='/join'>회원가입</a></div></div>
                </form>
                <p className="text-center text-gray-500 text-xs">
                    &copy;2024 Minsu Corp. All rights reserved.
                </p>
            </div>
        </div>
    );
}

export default LoginPage;
