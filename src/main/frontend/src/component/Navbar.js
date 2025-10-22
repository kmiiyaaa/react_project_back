import { Link } from "react-router-dom";
import "./Navbar.css";

function Navbar({ user, onLogout }) {
  return (
    <div className="navbar">
      <div>🏡맛집List🍽️</div>
      <div className="menu">
        <Link to="/">Home</Link>
        <Link to="/image-board">맛집포스팅(준비중)</Link>
        <Link to="/board">자유게시판</Link>
        {!user && <Link to="/login">로그인</Link>}
        {!user && <Link to="/signup">회원가입</Link>}

        {user && <button onClick={onLogout}>로그아웃</button>}
      </div>
    </div>
  );
}

export default Navbar;
