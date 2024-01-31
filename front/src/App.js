import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Login from './Login';
import SignUp from './SignUp';
import Home from './pages/main/Home';
import DetailPage from './pages/detail/DetailPage';
import SignInPage from './Auth/SignInPage';
import SignUpPage from './Auth/SignUpPage';
import Register from './pages/store/Register';
import RegisterUpdate from './pages/store/RegisterUpdate';
import 'bootstrap/dist/css/bootstrap.css';

function App() {
  return (
    <Router>
      <div>
        {/* <nav>
          <ul>
            <li>
              <Link to="/home">홈</Link>
            </li>
            <li>
              <Link to="/login">로그인</Link>
            </li>
            <li>
              <Link to="/signup">회원가입</Link>
            </li>
          </ul>
        </nav> */}
        
        
        <Routes>
          <Route path="/" element={<Home />} />
          {/* <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} /> */}
          <Route path="/detail" element={<DetailPage />} />
          <Route path="signin" element={<SignInPage />} />
          <Route path="signup" element={<SignUpPage />} />
          <Route path="register" element={<Register />} />
          <Route path="registerupdate" element={<RegisterUpdate />} />
        </Routes>
      </div>
    </Router>
  );
}



export default App;
