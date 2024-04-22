import React from 'react';
import HotSlider from '../components/HotSlider';

import './HomePage.css'

function HomePage() {

    return (
        <div className="container mx-auto p-4 mb-40">
            <div className="home-page-banner">
                <img src="images/banner.png" alt="Home Page Banner" className="banner-image"/>
                <img src="images/연꽃.png" alt="Home Page Banner" className="banner-image2"/>
                <img src="images/person.png" alt="Home Page Banner" className="banner-image3"/>
                <img src="images/gift.png" alt="Home Page Banner" className="banner-image4"/>
                <img src="images/person.png" alt="Home Page Banner" className="banner-image5"/>
                <img src="images/good.png" alt="Home Page Banner" className="banner-image6"/>
                <div className="banner-content">
                    <h1 className="banner-title">믿을만한(아마도)<br />중고 거래 플랫폼</h1>
                    <p className="banner-description">연근마냥 아삭아삭한 중고거래 <br /> 지금 당장 경험해보세요!</p>
                </div>
            </div>

            <HotSlider />
        </div>
    );
}

export default HomePage;
