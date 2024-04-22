import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import sessionService from '../services/sessionService';

function Header() {
    const [isMenuOpen, setIsMenuOpen] = useState(false);
    const [searchQuery, setSearchQuery] = useState('');
    const navigate = useNavigate();
    const user = sessionService.getUser();

    const toggleMenu = () => {
        setIsMenuOpen(!isMenuOpen);
    };

    const handleSearch = () => {
        navigate(`/search?query=${encodeURIComponent(searchQuery)}`);
        setSearchQuery('');
    };

    const handleSearchChange = (event) => {
        setSearchQuery(event.target.value);
    };


    const handleMyPage = () => {
        setIsMenuOpen(!isMenuOpen);
        if(!user) {
            alert('접근하려면 로그인을 하십시오');
            navigate('/login');
        } else {
            navigate('/my-page');
        }
    }

    const handleHotPosts = () => {
        setIsMenuOpen(!isMenuOpen);
        navigate('/post/hot');
    }

    const handleLogout = () => {
        setIsMenuOpen(!isMenuOpen);
        sessionService.clearUser();
        navigate('/login');
    };

    const menuStyle = {
        transform: isMenuOpen ? 'translateX(0)' : 'translateX(-100%)',
        transition: 'transform 300ms ease-in-out',
      };

  return (
    <>
        <div className="flex items-center justify-between px-4 min-h-[5rem] bg-white shadow-md">
            {/* 메뉴 토글 버튼 */}
            <button id="menuButton" onClick={toggleMenu} className="focus:outline-none">
                <img src="images/menu_icon.png" alt="Logo" className="h-12 w-12" />
            </button>

            {/* 로고 (홈으로 이동) */}
            <Link to="/" className="flex items-center">
                <img src="images/연근.png" alt="Logo" className="h-16 w-16" /><div className="font-bold text-2xl text-orange-300">연근마켓</div>
            </Link>

            {/* 검색 영역 */}
            <div className="flex items-center space-x-2">
                <input
                    type="text"
                    value={searchQuery}
                    onChange={handleSearchChange}
                    placeholder="물품을 검색해 보세요!"
                    className="px-4 py-2 border rounded"
                />
                <button onClick={handleSearch} className="bg-gray-200 px-4 py-2 rounded text-sm hover:bg-gray-400 transition-colors">
                    검색
                </button>
            </div>
        </div>

        {/* 메뉴 슬라이드 영역 */}
        <div
            style={menuStyle}
            className="fixed z-50 left-0 top-0 w-80 h-full bg-custom-pink1 z-5 border"
        >
            {/* 메뉴 컨텐츠 */}
            <div className="flex justify-between items-center mb-8 pl-4">
                {user ? (
                        <>
                            <button
                                onClick={handleLogout}
                                className="inline-block px-4 py-2 p-l text-sm font-semibold text-white bg-gray-500 rounded shadow hover:bg-gray-700 transition-colors"
                            >
                                로그아웃
                            </button>
                            <span>{user.role === 'ROLE_USER' ? `${user.username} 사용자님` : `${user.username} 관리자님`}</span>
                        </>
                    ) : (
                            <Link
                                to="/login"
                                onClick={toggleMenu}
                                className="inline-block px-4 py-2 p-l text-sm font-semibold text-white bg-gray-500 rounded shadow hover:bg-gray-700 transition-colors"
                            >
                                로그인
                            </Link>
                    )}
                <button
                    onClick={toggleMenu}
                    className="p-2 rounded-full"
                >
                    <img src="images/back.png" alt="Back_btn" className="h-16 w-14" />
                </button>
            </div>

            {/* MY 페이지 링크 */}
            <div className="mb-4 pl-5">
                <button onClick={handleMyPage} className="text-left font-bold w-full no-underline hover:underline">MY 연근(로그인 필요)</button>
                <button onClick={handleHotPosts} className="text-left font-bold w-full no-underline hover:underline pt-4">현재 HOT 매물 LIST</button>
            </div>

            {/* 나머지 링크들 */}
            <div className="mt-8">
                <h3 className="text-xl font-semibold mb-4 pl-3">카테고리별 매물</h3>
                <div className="border-t border-b border-gray-400 py-4">
                <Link to="/post" className="block py-2 px-4 hover:bg-gray-200" onClick={toggleMenu}>📄전체 목록</Link>
                <Link to="/post/category/1" className="block py-2 px-4 hover:bg-gray-200" onClick={toggleMenu}>💻디지털 기기</Link>
                <Link to="/post/category/2" className="block py-2 px-4 hover:bg-gray-200" onClick={toggleMenu}>🪑가구/인테리어</Link>
                <Link to="/post/category/3" className="block py-2 px-4 hover:bg-gray-200" onClick={toggleMenu}>🏠생활 가전</Link>
                <Link to="/post/category/4" className="block py-2 px-4 hover:bg-gray-200" onClick={toggleMenu}>🏀스포츠/레저</Link>
                <Link to="/post/category/5" className="block py-2 px-4 hover:bg-gray-200" onClick={toggleMenu}>🕹️취미/게임/음반</Link>
                <Link to="/post/category/6" className="block py-2 px-4 hover:bg-gray-200" onClick={toggleMenu}>🍖식품</Link>
                </div>
            </div>

            {/* 하단 버튼들 */}
            <div className="absolute bottom-0 left-0 right-0 p-6 space-y-4">
                <div className='flex justify-center items-center'>
                    <button onClick={toggleMenu} className="inline-block px-4 py-2 p-l w-1/2 text-sm font-semibold text-white bg-violet-400 rounded shadow hover:bg-violet-600 transition-colors">💬내 거래 채팅</button>
                </div>
                <div className="flex justify-center items-center">
                    <a href='/notice' className='no-underline hover:underline pr-8' onClick={toggleMenu}>📣공지사항</a>
                    <a href='/' className='no-underline hover:underline' onClick={toggleMenu}>📝약관 및 정책</a>
                </div>
            </div>
            
        </div>
    </>

  );
}

export default Header;