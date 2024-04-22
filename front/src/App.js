import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import HomePage from './pages/HomePage';
import MyPage from './pages/MyPage';
import LoginPage from './pages/LoginPage';
import JoinPage from './pages/JoinPage';
import NoticePage from './pages/NoticePage';
import NoticeFormPage from './pages/NoticeFormPage';
import NoticeDetailPage from './pages/NoticeDetailPage';
import PostPage from './pages/PostPage';
import PostDetailPage from './pages/PostDetailPage';
import PostFormPage from './pages/PostFormPage';

import "./index.css";
import { AuthProvider } from './context/AuthContext';
import Header from './components/Header';
import Footer from './components/Footer';


function App() {

  return (<>
      <AuthProvider>
        <Router>
          <Header />
          <Routes>
            <Route path='/' exact element={<HomePage/>} />
            <Route path='/login' element={<LoginPage/>} />
            <Route path='/join' element={<JoinPage/>} />
            <Route path='/notice' element={<NoticePage/>} />
            <Route path='/notice-form' element={<NoticeFormPage/>} />
            <Route path='/notice-form/:id' element={<NoticeFormPage/>} />
            <Route path='/notice/:id' element={<NoticeDetailPage/>} />
            <Route path='/post' element={<PostPage/>} />
            <Route path='/post/hot' element={<PostPage/>} />
            <Route path='/search' element={<PostPage/>} />
            <Route path='/post/like/:id' element={<PostPage/>} />
            <Route path='/post/category/:id' element={<PostPage/>} />
            <Route path='/post/user/:id' element={<PostPage/>} />
            <Route path='/post/:id' element={<PostDetailPage/>} />
            <Route path='/post-form' element={<PostFormPage/>} />
            <Route path='/post-form/:id' element={<PostFormPage/>} />
            <Route path='/my-page' element={<MyPage/>} />
          </Routes>
          <Footer />
        </Router>
      </AuthProvider>

    </>
  );
}

export default App;
