import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { fetchNoticeDetail, fetchCategories, createNotice, updateNotice } from '../services/noticeService';
import sessionService from '../services/sessionService';

function NoticeFormPage() {
    const [title, setTitle] = useState('');
    const [category, setCategory] = useState('');
    const [categories, setCategories] = useState([]);
    const [content, setContent] = useState('');
    const [images, setImages] = useState([]);  // 이미지 경로를 저장하는 배열
    const user = sessionService.getUser();
    const { id } = useParams();
    const navigate = useNavigate();

    // 카테고리 데이터 로딩
    useEffect(() => {
        document.body.classList.add('bg-slate-200');
        const loadCategories = async () => {
            try {
                const categoryData = await fetchCategories();
                // console.log("Categories Loaded:", categoryData);
                setCategories(categoryData);
                if (categoryData.length > 0) {
                    setCategory(categoryData[0].idx); // 초기 카테고리 ID로 설정
                }
            } catch (error) {
                console.error('카테고리를 불러오는 중 에러 발생', error);
            }
        };

        if (id) {
            fetchNoticeDetail(id).then((data) => {
                setTitle(data.title);
                setCategory(data.categoryId);
                setContent(data.content);
            });
        }

        loadCategories();

        return () => {
            document.body.classList.remove('bg-slate-200');
        };
    }, [id]);

    const handleSubmit = async (event) => {
        event.preventDefault();
        const formData = new FormData();
        formData.append('title', title);
        formData.append('content', content);
        formData.append('categoryId', category);
        formData.append('userId', user.userId);
        images.forEach(image => {
            formData.append('images', image); // 'images'라는 이름으로 모든 이미지 파일을 추가
        });
        
        console.log("formData 점검" + Array.from(formData.entries()));
        try {
            if (id) {
                await updateNotice(id, formData);
            } else {
                await createNotice(formData);
            }
            navigate('/notice');
        } catch (error) {
            console.error('공지사항 저장 중 에러 발생', error);
        }
    };

    const handleImageUpload = (event) => {
        setImages([...images, ...Array.from(event.target.files)]); // 파일 배열에 추가
    };

    const removeImage = (index) => {
        setImages(images.filter((_, i) => i !== index)); // 인덱스를 사용하여 이미지 삭제
    };

    return (
        <div className="container mx-auto p-4">
            <div className="bg-gray-100 p-6 rounded-lg shadow-lg min-h-screen">
                <div className="container mx-auto p-4 flex items-center justify-between">
                    <button onClick={() => navigate(-1)} className="mb-4">
                        🔙
                    </button>
                    <h2 className="text-xl font-semibold mb-2">공지 작성 폼</h2>
                    <div className='pl-10'></div>
                </div>
                <div className='pl-20 pr-20'>
                    <form onSubmit={handleSubmit} className="space-y-6">
                        <div>
                            <label htmlFor="title" className="block text-sm font-medium text-gray-700">제목</label>
                            <input
                                id="title"
                                name="title"
                                type="text"
                                required
                                className="mt-1 block w-4/5 border-gray-300 rounded-md shadow-sm"
                                placeholder="제목을 입력해 주세요"
                                value={title}
                                onChange={(e) => setTitle(e.target.value)}
                            />
                        </div>

                        <div>
                            <label htmlFor="category" className="block text-sm font-medium text-gray-700">카테고리</label>
                            <select
                                id="category"
                                name="category"
                                required
                                className="mt-1 block w-1/5 border-gray-300 rounded-md shadow-sm"
                                value={category}
                                onChange={(e) => {
                                    console.log("Actual option element:", e.target.options[e.target.selectedIndex]); // 실제 선택된 option 요소 검사
                                    setCategory(e.target.value);
                                }}
                            >
                                {categories.map((cat) => (
                                    <option key={cat.idx} value={cat.idx}>
                                        {cat.name}
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div>
                            <label htmlFor="image-upload" className="block text-sm font-medium text-gray-700">사진 업로드</label>
                            <input
                                id="image-upload"
                                name="image-upload"
                                type="file"
                                multiple // 여러 파일 선택 가능
                                className="mt-1 block w-full text-sm text-gray-700"
                                onChange={handleImageUpload}
                            />
                            <div className="mt-2">
                                {images.map((image, index) => (
                                    <div key={index} className="flex items-center space-x-2">
                                        <span>{image.name}</span>
                                        <button type="button" onClick={() => removeImage(index)}>X</button>
                                    </div>
                                ))}
                            </div>
                        </div>

                        <div>
                            <label htmlFor="content" className="block text-sm font-medium text-gray-700">내용</label>
                            <textarea
                                id="content"
                                name="content"
                                rows="4"
                                required
                                className="mt-1 block w-full border-gray-300 rounded-md shadow-sm"
                                placeholder="내용을 입력해 주세요"
                                value={content}
                                onChange={(e) => setContent(e.target.value)}
                            ></textarea>
                        </div>

                        <div className="flex justify-end">
                            <button
                                type="submit"
                                className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
                            >
                                등록하기
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        
    );
}

export default NoticeFormPage;
