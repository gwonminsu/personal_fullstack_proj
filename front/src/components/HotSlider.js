import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import Slider from 'react-slick';
import { fetchHotPosts, fetchPostDetail } from '../services/postService';
import { fetchLikeCount } from '../services/likeService';
import './HotSlider.css';

const HotSlider = () => {
    const [hotPosts, setHotPosts] = useState([]);
    const [thumbnails, setThumbnails] = useState({});
    const [contents, setContents] = useState({});
    const [likeCounts, setLikeCounts] = useState({});

    // 게시물들의 이미지 경로를 가져오는 함수
    const fetchThumbnails = async (posts) => {

        const thumbnailPromises = posts.map(async (post) => {
            const detail = await fetchPostDetail(post.id);
            // console.dir(detail);
            if (detail?.imagePaths) {
                const firstImagePath = detail.imagePaths.split(';')[0];
                return { id: post.id, imagePath: firstImagePath };
            }
            return { id: post.id, imagePath: null };
    });

        const thumbnails = await Promise.all(thumbnailPromises);
        const thumbnailsMap = thumbnails.reduce((acc, { id, imagePath }) => {
            if (imagePath) {
                acc[id] = imagePath;
            }
            return acc;
        }, {});

        setThumbnails(thumbnailsMap);
        console.dir(thumbnailsMap);
    };

    const fetchContents = async (posts) => {
        const contentPromises = posts.map(async (post) => {
        const detail = await fetchPostDetail(post.id);
        // 만약 detail이 정의되어 있고, detail.content가 존재한다면 그 값을 반환합니다.
        if (detail?.content) {
            return { id: post.id, content: detail.content };
        }
        return { id: post.id, content: null };
        });

        const contents = await Promise.all(contentPromises);
        const contentsMap = contents.reduce((acc, { id, content }) => {
        if (content) {
            acc[id] = content;
        }
        return acc;
        }, {});

        setContents(contentsMap);
    };

    useEffect(() => {
        const getHotPosts = async () => {
            let data = await fetchHotPosts();
            // 서버로부터 가져온 데이터가 배열일 경우, 첫 10개의 항목만 선택
            data = data.slice(0, 12);
            setHotPosts(data);
            if (data.length > 0) {
                await fetchThumbnails(data);
                await fetchContents(data);
            }
    };

        getHotPosts();
    }, []);

    useEffect(() => {
        const fetchLikesCounts = async (posts) => {
        const likeCounts = {};
        for (let hotPost of posts) {
                const countLike = await fetchLikeCount(hotPost.id);
                likeCounts[hotPost.id] = countLike;
            }
            setLikeCounts(likeCounts)
        };
    
        if (hotPosts.length > 0) {
            fetchLikesCounts(hotPosts);
        }
    }, [hotPosts]);

    const settings = {
        dots: true,
        infinite: true,
        speed: 500,
        slidesToShow: 3,
        slidesToScroll: 3,
        autoplay: true,  // 자동 넘김 활성화
        autoplaySpeed: 5000,
        pauseOnHover: true,
    };

    return (
        <div className='hot-slider-container'>
            <Link to="/post/hot"><h2 className="hot-slider-title">현재 HOT 매물🔥</h2></Link>
            <div className=" font-light text-center text-sm text-gray-600">거래가 완료되지 않은 가장 많은 조회수를 가진 게시물들이에요!</div>
            <Slider {...settings}>
                {hotPosts.map((post) => (
                    <div key={post.id} className="hot-slider-item">
                        <div className="hot-slider-image-container">
                            <img src={thumbnails[post.id] || 'images/no-img.png'} alt="Thumbnail" className="hot-slider-image" />
                        </div>
                        <div className="hot-slider-content">
                            <Link to={`/post/${post.id}`}>
                                <h3 className="hot-slider-post-title">
                                    {post.title && post.title.length > 15 ? `${post.title.substring(0, 10)}...` : post.title}
                                </h3>
                            </Link>
                            
                            <p className="hot-slider-post-description">
                                {contents[post.id] && contents[post.id].length > 10 ? `${contents[post.id].substring(0, 10)}...` : contents[post.id]}
                            </p>
                            <div className="hot-slider-post-price">
                                {post.price.toLocaleString('ko-KR')}원
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
                                <div>❤️{likeCounts[post.id]}</div>
                            </div>
                        </div>
                    </div>
                ))}
            </Slider>
        </div>
    );
};

export default HotSlider;
