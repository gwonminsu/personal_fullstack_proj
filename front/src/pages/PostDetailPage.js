import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { fetchPostDetail, deletePost } from '../services/postService';
import sessionService from '../services/sessionService';
import ImageSlider from '../components/ImageSlider';
import Comment from '../components/Comment';
import { addLike, deleteLike, fetchLikeCount, fetchLikeUserIds } from '../services/likeService';

function PostDetailPage() {
    const [post, setPost] = useState(null);
    const [likeDatas, setLikeDatas] = useState([]);
    const [likeCount, setLikeCount] = useState(0);
    const [isLiked, setIsLiked] = useState(false);
    const [hover, setHover] = useState(false); // 찜 버튼 호버 관리
    const { id } = useParams();
    const navigate = useNavigate();
    const user = sessionService.getUser();

    useEffect(() => {
        document.body.classList.add('bg-slate-200');
        const loadPostDetail = async () => {
            try {
                const data = await fetchPostDetail(id);
                setPost(data);
                const likeData = await fetchLikeCount(id);
                setLikeCount(likeData);
                const likeUsers = await fetchLikeUserIds(id);
                setLikeDatas(likeUsers);
                if(user) {
                    const userLiked = likeUsers.some(like => like.userId === user.userId);
                    setIsLiked(userLiked);
                } else {
                    setIsLiked(false);
                }   
            } catch (error) {
                console.error('게시물 상세 정보를 불러오는 중 에러 발생', error);
                alert("이미 삭제된 게시물입니다.");
                navigate(-1); // 이전 페이지로 돌아가기
            }
        };
        loadPostDetail();

        return () => {
            document.body.classList.remove('bg-slate-200');
        };
    }, [id]);

    const toggleLike = async () => {
        try {
            if (isLiked) {
                const likeId = likeDatas.find(like => like.userId === user.userId)?.id;
                if (!likeId) throw new Error('Like ID를 찾을 수 없음');
                await deleteLike(likeId);
                setLikeDatas(prev => prev.filter(like => like.id !== likeId));
                setLikeCount(prev => prev - 1);
                setIsLiked(false);
            } else {
                const addedLike = await addLike(post.id, user.userId);
                setLikeDatas(prev => [...prev, addedLike]);
                setLikeCount(prev => prev + 1);
                setIsLiked(true);
            }
        } catch (error) {
            console.error('like 토글 오류:', error);
            alert('좋아요 상태를 업데이트하는 동안 오류가 발생함');
        }
    };

    const handleDelete = async () => {
        await deletePost(id);
        navigate(-1);
        alert("해당 게시글 삭제 완료");
    };

    if (!post) {
        return <div>Loading...</div>;
    }

    // 수정 및 삭제 버튼 표시 조건
    const canEditOrDelete = user && (user.role === 'ROLE_ADMIN' || user.userId === post.userId);

    // 채팅 버튼 표시 조건
    const canChatting = !(user && (user.userId === post.userId))

    return (
        <div className="container mx-auto p-4">
            <div className="bg-gray-100 p-6 rounded-lg shadow-lg min-h-screen">
                <div className="container mx-auto p-4 flex relative">
                    <button onClick={() => navigate(-1)} className="absolute left-4 mb-4">
                        🔙
                    </button>
                    <h2 className="text-xl font-semibold mb-2 text-center w-full">
                        {post.title}
                        <span
                            className={`px-2 py-1 ml-3 text-xs text-white rounded ${
                                post.status === '판매 중'
                                    ? 'bg-green-500'
                                    : post.status === '예약 중'
                                    ? 'bg-orange-500'
                                    : 'bg-red-500'
                            }`}
                        >
                            {post.status}
                        </span>
                    </h2>
                    <span className="absolute right-4 tag-label">
                        { canChatting && (
                            <button className="inline-block mr-3 px-4 py-2 p-l w-fit text-sm font-semibold text-white bg-violet-400 rounded shadow hover:bg-violet-600 transition-colors">💬채팅하기</button>
                        )}
                        
                        <button
                            onClick={toggleLike}
                            onMouseOver={() => setHover(true)} // 마우스가 버튼 위에 있을 때
                            onMouseOut={() => setHover(false)} // 마우스가 버튼에서 벗어날 때
                            style={{ fontSize: '24px' }}
                        >
                            {hover ? '💛' : (isLiked ? '❤️' : '🖤')}
                        </button>
                        <span className='mr-3'>{likeCount}</span>
                        {post.categoryName}
                    </span>
                </div>
                <div className="my-4 flex justify-center">
                    {post.imagePaths && <ImageSlider images={post.imagePaths.split(';')} />}
                </div>
                <div className="flex justify-between items-center my-4">
                    <span className="flex-1 flex justify-start items-center text-sm font-medium"><img src="/images/user_ico.png" alt="User" className="h-8 w-8 mr-2" /><strong>작성자: </strong>{post.userName}</span>
                    <span className="flex-1 flex justify-center items-center text-sm font-medium"><strong className='mr-2'>판매 금액: </strong>{post.price.toLocaleString('ko-KR')}원</span>
                    <span className="flex-1 flex justify-end items-center text-sm text-gray-500">
                        <span className="text-gray-500 font-light mr-5">👁️{post.hit}</span>{new Date(post.createdAt).toLocaleString('ko-KR')}
                    </span>
                </div>
                <hr />
                <p className="text-base text-gray-500 mt-3 min-h-[50vh]">{post.content}</p>

                <div className='fixed bottom-1 right-1 m-4'>
                    {canEditOrDelete && (
                        <div className="flex flex-row items-end space-y-2">
                            <button
                                onClick={() => navigate(`/post-form/${id}`)}
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
                <div className='mt-5 w-full'>
                    <Comment postId={id}/>
                </div>
                
            </div>
        </div>
    );
}

export default PostDetailPage;
