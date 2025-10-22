import { useState } from "react";
import "./BoardWrite.css";
import { useNavigate } from "react-router-dom";
import api from "../api/axiosConfig";

function BoardWrite({ user }) {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault(); //페이지 새로고침 방지
    setErrors({});

    //로그인한 유저만 글쓰기 허용
    if (!user) {
      alert("로그인한 유저만 글 작성이 가능합니다.");
      return;
    }

    try {
      await api.post("/api/board", { title, content });
      alert("글 작성 성공!");
      navigate("/board");
    } catch (err) {
      if (err.response && err.response.status == 400) {
        setErrors(err.response.data);
      } else {
        console.error(err);
        alert("글쓰기 실패");
      }
    }
  };

  return (
    <div className="board-write-container">
      <h2>글쓰기</h2>
      <form onSubmit={handleSubmit}>
        <input
          placeholder="제목"
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        ></input>
        <p style={{ color: "red" }}>{errors.title}</p>
        <textarea
          placeholder="내용"
          value={content}
          onChange={(e) => setContent(e.target.value)}
        ></textarea>

        <p style={{ color: "red" }}>{errors.content}</p>

        <div>
          <button type="submit">등록</button>
          <button type="button" onClick={() => navigate("/board")}>
            취소
          </button>
        </div>
      </form>
    </div>
  );
}

export default BoardWrite;
