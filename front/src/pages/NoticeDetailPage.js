import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { fetchNoticeDetail, deleteNotice } from '../services/noticeService';
import sessionService from '../services/sessionService';
import ImageSlider from '../components/ImageSlider';

function NoticeDetailPage() {
  const [notice, setNotice] = useState(null);
  const { id } = useParams();
  const navigate = useNavigate();
  const user = sessionService.getUser();

  useEffect(() => {
    document.body.classList.add('bg-slate-200');
    const loadNoticeDetail = async () => {
      try {
        const data = await fetchNoticeDetail(id);
        setNotice(data);
      } catch (error) {
        console.error('공지 상세 정보를 불러오는 중 에러 발생', error);
      }
    };
    loadNoticeDetail();

    return () => {
      document.body.classList.remove('bg-slate-200');
    };
  }, [id]);

  const handleDelete = async () => {
    await deleteNotice(id);
    navigate('/notice');
    alert("해당 게시글 삭제 완료");
  };

  if (!notice) {
    return <div>Loading...</div>;
  }

  return (
    <div className="container mx-auto p-4">
      <div className="bg-gray-100 p-6 rounded-lg shadow-lg min-h-screen">
        <div className="container mx-auto p-4 flex items-center justify-between">
          <button onClick={() => navigate(-1)} className="mb-4"> {/* useNavigate를 사용하여 페이지 뒤로 이동 */}
            🔙
          </button>
          <h2 className="text-xl font-semibold mb-2">{notice.title}</h2>
          <span className="tag-label">{notice.categoryName}</span>
        </div>
        <div className="my-4 flex justify-center">
          {notice.imagePaths && <ImageSlider images={notice.imagePaths.split(';')} />}
        </div>
        <div className="flex justify-between items-center my-4">
        <span className="flex items-center text-sm font-medium"><img src="/images/user_ico.png" alt="User" className="h-8 w-8 mr-2" /><strong>작성자: </strong>{notice.userName}</span>
          <span className="text-sm text-gray-500">
            <span className="text-gray-500 font-light mr-5">👁️{notice.hit}</span>{new Date(notice.createdAt).toLocaleString('ko-KR')}
          </span>
        </div>
        <hr />
        <p className="text-base text-gray-500">{notice.content}</p>

        <div className='fixed bottom-1 right-1 m-4'>
          {user && user.role === 'ROLE_ADMIN' && (
            <div className="flex flex-row items-end space-y-2">
              <button
                onClick={() => navigate(`/notice-form/${id}`)}
                className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mr-2"
              >
                수정
              </button>
              <button
                onClick={handleDelete}
                className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded"
              >
                삭제
              </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default NoticeDetailPage;
