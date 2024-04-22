import React from 'react';

function Footer() {
  return (
    <footer className="bg-gray-400 text-white text-center p-4 flex justify-center items-center">
        <div className="flex flex-col justify-center items-center px-6">
            <img src="images/연근.png" alt="Logo" className="h-16 w-16 mr-2" />
            <div className="font-bold text-2xl text-orange-300">연근마켓</div>
        </div>

        <div className="flex flex-col items-center px-6">
            <p className="text-sm mb-2">
            © {new Date().getFullYear()} 연근마켓. All rights reserved.
            </p>
            <div className="flex space-x-4 mb-2">
            <a href="/" className="hover:text-gray-200">이용 약관</a>
            <a href="/" className="hover:text-gray-200">개인정보 처리방침</a>
            <a href="/" className="hover:text-gray-200">고객지원</a>
            </div>
            <div className="text-xs mb-2">
            <p>고객센터: 123-456-7890 (월~금 09:00~18:00)</p>
            <p>Email: kd0615@naver.com</p>
            </div>
            <p>주소: 경산북도 경산시 어딘가 그쯤</p>
        </div>
    </footer>
  );
}

export default Footer;
