import { Link } from 'react-router-dom';
import Login from '../Login';
import SignUp from '../SignUp';

const Header = () => {
    return (
        <header>
            <nav>
                <ul>
                    <li>
                        <Link to="/">홈</Link>
                    </li>
                    <li>
                        <Link to="/login">로그인</Link>
                    </li>
                    <li>
                        <Link to="/signup">회원가입</Link>
                    </li>
                </ul>
            </nav>
        </header>
    );
}

export default Header;