import { Route, Routes } from "react-router-dom";
import Login from "../Login";
import SignUp from "../SignUp";
import Main from "../pages/Main";
import Register from "../location/Register";
import RegisterUpdate from "../location/RegisterUpdate";

const Footer = () => {
    return (
        <footer>
            <Routes>
                <Route path="/" element={<Main />} />
                <Route path="/login" element={<Login />} />
                <Route path="/signup" element={<SignUp />} />
                <Route path="/register" element={<Register />} />
                <Route path="/update" element={<RegisterUpdate />} />
            </Routes>
        </footer>
    )
}

export default Footer;