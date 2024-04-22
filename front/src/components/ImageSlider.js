import React from 'react';
import Slider from 'react-slick';
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import "./ImageSlider.css"; // 추가된 CSS 파일

const ImageSlider = ({ images }) => {
    const settings = {
        dots: true,
        infinite: false,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1,
        adaptiveHeight: true,
        centerMode: true, // 이미지가 슬라이더의 중앙에 위치하도록 함
        variableWidth: true, // 각 슬라이드의 너비가 다를 수 있음
        swipeToSlide: false, // 사용자가 슬라이드를 드래그할 수 있게 함
        swipe: false,  // 드래그 기능 비활성화
        draggable: false, // 마우스 드래그 비활성화
        focusOnSelect: true, // 선택된 슬라이드에 포커스 맞추기
        centerPadding: '50px', // 중앙 이미지의 양 옆 패딩
    };

    return (
        <div className='image-slider'>
            <Slider {...settings}>
                {images.map((image, index) => (
                    <div key={index} className="slide">
                        <img src={image} alt={`Slide ${index}`} className="w-72 h-72 slide-image" />
                    </div>
                ))}
            </Slider>
        </div>
    );
};

export default ImageSlider;
