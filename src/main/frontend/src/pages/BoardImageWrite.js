import { useState } from "react";
import "./BoardImageWrite.css";
import { useNavigate } from "react-router-dom";

function BoardImageWrite() {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [image, setImage] = useState(null);
  const [preview, setPreview] = useState(null);
  const [loading, setloading] = useState(false);
  const navigate = useNavigate();

  //이미지 미리보기
  const handleImageChange = (e) => {
    const file = e.target.files[0];
    setImage(file);

    if (file) {
      setPreview(URL.createObjectURL(file));
    } else {
      setPreview(null);
    }
  };

  //글 등록
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!user) {
      alert("로그인 후 글 작성 가능");
      return;
    }

    const formData = new FormData();
    formData.append("title", title);
    formData.append("content", content);

    if (image) {
      formData.append("image", image);
    }

    try {
      setloading(true);
      await api.post("/api/image-board/write");
      alert();
      navigate("/image-board");
    } catch (err) {
      console.error(err);
      alert("게시글 등록에 실패했습니다.");
    } finally {
      setloading(false);
    }
  };

  return (
    <div className="image-write-container">
      <h2>📸 맛집 포스팅 등록</h2>
      <form>
        <label>제목</label>
        <input
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          placeholder="제목을 입력하세요"
        />

        <label>내용</label>
        <textarea
          value={content}
          onChange={(e) => setTitle(e.target.value)}
          placeholder="맛집에 대한 설명 작성"
        />

        <label>이미지 첨부</label>
        <input type="file" accept="image/*" onChange={handleImageChange} />
      </form>
    </div>
  );
}

export default BoardImageWrite;
