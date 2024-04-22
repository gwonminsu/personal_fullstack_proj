import React, { useEffect, useState } from 'react';
import { useParams, Link, useLocation, useSearchParams } from 'react-router-dom';
import { fetchPosts, fetchCategoryPosts, fetchCategories, fetchHotPosts, fetchUserPosts, fetchPostOne, fetchPostsBySearchQuery } from '../services/postService';
import { fetchCommentCount } from '../services/commentService';
import sessionService from '../services/sessionService';
import { fetchLikeCount, fetchLikePostIds } from '../services/likeService';

function PostPage() {
    const { id } = useParams(); // 카테고리 ID를 URL에서 추출
    const [posts, setPosts] = useState([]);
    const [categories, setCategories] = useState([]); // 카테고리 DB 목록
    const [currentPage, setCurrentPage] = useState(1);
    const [postsPerPage] = useState(10); // 한 페이지에 표시될 게시물 수
    const [commentCounts, setCommentCounts] = useState({}); // 댓글 개수를 저장하는 객체 상태
    const [likeCounts, setLikeCounts] = useState({}); // 찜 개수를 저장할 상태
    const [searchParams] = useSearchParams();

    const location = useLocation();
    const user = sessionService.getUser();

    useEffect(() => {
        document.body.classList.add('bg-slate-200');
    
        // 게시물 목록을 가져오는 로직
        const fetchPostsData = async () => {
            try {
                let data = [];
                const query = searchParams.get("query");
                if (location.pathname.includes('/post/hot')) {
                    data = await fetchHotPosts();
                } else if(location.pathname.includes('/post/user')) {
                    data = await fetchUserPosts(user.userId);
                } else if(location.pathname.includes('/post/like')) {
                    const likedPostIds = await fetchLikePostIds(user.userId); // 사용자가 찜한 게시물 ID 목록을 가져옴
                    for(let i=0; i<likedPostIds.length; i++) {
                        data.push(await fetchPostOne(likedPostIds[i].postId));
                    }
                } else if(location.pathname.includes('/search') && query) {
                    data = await fetchPostsBySearchQuery(query);
                } else if (id) {
                    data = await fetchCategoryPosts(id);
                } else {
                    data = await fetchPosts();
                }
                const categoriesData = await fetchCategories();
                setCategories(categoriesData);
                setPosts(data);
            } catch (error) {
                console.error('게시물 로딩 에러', error);
            }
        };
        fetchPostsData();
    
        return () => {
        document.body.classList.remove('bg-slate-200');
        };
    }, [id, location.pathname, searchParams]);
    
    // posts 상태가 업데이트 되었을 때 댓글 개수와 찜 개수를 가져오는 로직
    useEffect(() => {
        const fetchCommentsAndLikesCounts = async (posts) => {
        const commentCounts = {};
        const likeCounts = {};
        for (let post of posts) {
                const countComment = await fetchCommentCount(post.id);
                const countLike = await fetchLikeCount(post.id);
                commentCounts[post.id] = countComment;
                likeCounts[post.id] = countLike;
            }
            setCommentCounts(commentCounts);
            setLikeCounts(likeCounts)
        };
    
        if (posts.length > 0) {
            fetchCommentsAndLikesCounts(posts);
        }
    }, [posts]);

    // 타이틀 제목
    const headerText = location.pathname.includes('/post/hot') ? "현재 뜨는 매물🔥" :
    location.pathname.includes('/post/user') ? "내 판매 목록" :
    location.pathname.includes('/post/like') ? "내 찜 목록" :
    location.pathname.includes('/search') ? `'${searchParams.get("query")}'에 대한 중고 물품을 검색해 봤어요` :
    categories.find(cat => cat.idx.toString() === id)?.name || '전체 매물 목록';

    // 현재 페이지에 표시될 게시물 계산
    const indexOfLastPost = currentPage * postsPerPage;
    const indexOfFirstPost = indexOfLastPost - postsPerPage;
    const currentPosts = posts.slice(indexOfFirstPost, indexOfLastPost);

    // 페이지 번호를 클릭했을 때 실행될 함수
    const paginate = pageNumber => setCurrentPage(pageNumber);

    // 페이지네이션 버튼을 만드는 함수
    const pageNumbers = [];
    for (let i = 1; i <= Math.ceil(posts.length / postsPerPage); i++) {
        pageNumbers.push(i);
    }

    return (
        <div className="container mx-auto p-4">
            <div className="p-6 rounded-lg shadow-lg bg-gray-100 min-h-screen">
                <div className='flex justify-between pb-3'>
                    <h1 className="text-2xl font-bold mb-4">{headerText}</h1>

                </div>
                
                <div className="grid grid-cols-1 gap-4">
                    {currentPosts.map(post => (
                    <Link to={`/post/${post.id}`} key={post.id} className="block">
                        <div className="p-4 border rounded-lg">
                            <div className="flex justify-between items-center mb-2">
                                <h2 className="text-xl font-semibold">
                                    ({post.id}) {post.title}
                                    {commentCounts[post.id] > 0 && (
                                        <span className="text-red-500 bg-red-100 rounded-full px-2 py-1 ml-2 text-xs">
                                            댓글 {commentCounts[post.id]}
                                        </span>
                                    )}
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
                                <span className="text-gray-500 font-light flex justify-start">
                                    <strong className='pr-6'>가격: {post.price.toLocaleString('ko-KR')}원</strong>
                                    <div>👁️{post.hit}</div>
                                    <div className='ml-2'>❤️{likeCounts[post.id]}</div>
                                </span>
                            </div>
                            <div className="flex justify-between items-center text-sm mb-2">
                                <span><strong>작성자: </strong>{post.userName} [{post.categoryName}]</span>
                                <span>{new Date(post.createdAt).toLocaleString('ko-KR', {
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

export default PostPage;
