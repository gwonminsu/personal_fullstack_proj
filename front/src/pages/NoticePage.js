import React, { useState, useEffect } from 'react';
import { fetchNotices } from '../services/noticeService';
import sessionService from '../services/sessionService';
import { Link } from 'react-router-dom';

function NoticePage() {
    const [notices, setNotices] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [noticesPerPage] = useState(10); // 한 페이지에 표시될 공지사항 수

    const user = sessionService.getUser();

    useEffect(() => {
        document.body.classList.add('bg-slate-200');

        const loadNotices = async () => {
            try {
                const data = await fetchNotices();
                console.log(data);
                setNotices(data);
            } catch (error) {
                console.error('공지사항 로딩 에러', error);
            }
        };

        loadNotices();

        return () => {
            document.body.classList.remove('bg-slate-200');
          };
    }, []);

    // 현재 페이지에 표시될 공지사항 계산
    const indexOfLastNotice = currentPage * noticesPerPage;
    const indexOfFirstNotice = indexOfLastNotice - noticesPerPage;
    const currentNotices = notices.slice(indexOfFirstNotice, indexOfLastNotice);

    // 페이지 번호를 클릭했을 때 실행될 함수
    const paginate = pageNumber => setCurrentPage(pageNumber);

    // 페이지네이션 버튼을 만드는 함수
    const pageNumbers = [];
    for (let i = 1; i <= Math.ceil(notices.length / noticesPerPage); i++) {
        pageNumbers.push(i);
    }

    return (
        <div className="container mx-auto p-4">
            <div className="p-6 rounded-lg shadow-lg bg-gray-100 min-h-screen">
                <div className='flex justify-between pb-3'>
                    <h1 className="text-2xl font-bold mb-4">NOTICE📝</h1>
                    {user && user.role === 'ROLE_ADMIN' && (
                        <Link to="/notice-form" className="inline-block px-4 py-3 p-l text-sm font-semibold text-white bg-green-300 rounded shadow hover:bg-green-500 transition-colors">새 공지 작성(관리자 전용)</Link>
                    )}
                </div>
                
                <div className="grid grid-cols-1 gap-4">
                    {currentNotices.map(notice => (
                    <Link to={`/notice/${notice.id}`} key={notice.id} className="block">
                        <div className="p-4 border rounded-lg">
                            <div className="flex justify-between items-center mb-2">
                                <h2 className="text-xl font-semibold">({notice.id}) {notice.title}</h2>
                                <span className="text-gray-500 font-light">👁️{notice.hit}</span>
                            </div>
                            <div className="flex justify-between items-center text-sm mb-2">
                                <span><strong>작성자: </strong>{notice.userName} [{notice.categoryName}]</span>
                                <span>{new Date(notice.createdAt).toLocaleString('ko-KR', {
                                year: 'numeric',
                                month: '2-digit',
                                day: '2-digit',
                                hour: '2-digit',
                                minute: '2-digit',
                                second: '2-digit',
                                })}</span>
                            </div>
                        </div>
                    </Link>
                    ))}
                </div>
                <div className="flex justify-center mt-4 space-x-1">
                    {/* 이전 버튼 */}
                    <button
                        onClick={() => paginate(currentPage - 1)}
                        disabled={currentPage === 1}
                        className="px-3 py-1 rounded-md bg-gray-200 text-gray-600 hover:bg-gray-300 disabled:opacity-50 disabled:cursor-not-allowed"
                    >
                        이전
                    </button>

                    {/* 페이지 번호 버튼 */}
                    {pageNumbers.map(number => (
                        <button
                        key={number}
                        onClick={() => paginate(number)}
                        className={`px-3 py-1 rounded-md transition-colors duration-200 ${
                            currentPage === number
                            ? 'bg-yellow-200 text-yellow-800'
                            : 'bg-white text-gray-700 hover:bg-gray-200'
                        }`}
                        >
                        {number}
                        </button>
                    ))}

                    {/* 다음 버튼 */}
                    <button
                        onClick={() => paginate(currentPage + 1)}
                        disabled={currentPage === pageNumbers.length}
                        className="px-3 py-1 rounded-md bg-gray-200 text-gray-600 hover:bg-gray-300 disabled:opacity-50 disabled:cursor-not-allowed"
                    >
                        다음
                    </button>
                </div>
            </div>
        </div>
    );
}

export default NoticePage;
