import { Link } from 'react-router-dom';

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
                    <li>
                        <Link to="/register">매장(업소)등록</Link>
                    </li>
                    <li>
                        <Link to="/update">매장(업소)수정</Link>
                    </li>
                </ul>
            </nav>
        </header>
    );
}

export default Header;