import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Home from './pages/main/Home';
import InterestPlace from './pages/customer/InterestPlace';
import Header from './pages/main/Header';
import Login from './pages/user/LoginPage';
import Logout from './pages/user/LogoutPage';
import SignUp from './pages/user/SignUpPage';
import Detail from './pages/detail/DetailPage';


function App() {

  return (
    <Router>
      <Header />
      <div>        
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/InterestPlace" element={<InterestPlace />}/>
          <Route path="/logout" element={<Logout/>}/>
          <Route path="/detail" element={<Detail />} />
        </Routes>
      </div>
    </Router>
  );
}



export default App;
