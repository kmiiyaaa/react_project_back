import { Route, Routes } from "react-router-dom";
import "./App.css";
import Home from "./pages/Home";
import Navbar from "./component/Navbar";
import Signup from "./pages/Signup";
import Login from "./pages/Login";
import Board from "./pages/Board";
import { useEffect, useState } from "react";
import api from "./api/axiosConfig";
import BoardWrite from "./pages/BoardWrite";
import BoardDetail from "./pages/BoardDetail";
import ImageBoard from "./pages/ImageBoard";
import ImageBoardDetail from "./pages/ImageBoardDetail";

function App() {
  const [user, setUser] = useState(null); // 로그인 성공 -> 현재로그인한 유저이름

  const checkUser = async () => {
    try {
      const res = await api.get("api/auth/me");
      setUser(res.data.username);
    } catch {
      setUser(null);
    }
  };

  useEffect(() => {
    checkUser();
  }, []);

  const handleLogout = async () => {
    await api.post("api/auth/logout");
    setUser(null);
  };

  return (
    <div className="App">
      <Navbar onLogout={handleLogout} user={user} />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/login" element={<Login onLogin={setUser} />} />
        <Route path="/board" element={<Board user={user} />} />
        <Route path="/board/write" element={<BoardWrite user={user} />} />
        <Route path="/board/:id" element={<BoardDetail user={user} />} />
        <Route path="/image-board" element={<ImageBoard user={user} />} />
        <Route
          path="/image-board/:id"
          element={<ImageBoardDetail user={user} />}
        />
      </Routes>
    </div>
  );
}

export default App;
