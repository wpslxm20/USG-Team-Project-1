import { Route, Routes } from "react-router-dom";
import Login from "../Login";
import SignUp from "../SignUp";
import Main from "../pages/Main";

const Footer = () => {
    return (
        <footer>
            <Routes>
                <Route path="/" element={<Main />} />
                <Route path="/login" element={<Login />} />
                <Route path="/signup" element={<SignUp />} />
            </Routes>
        </footer>
    )
}

export default Footer;