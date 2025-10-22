import { useNavigate } from "react-router-dom";
import api from "../api/axiosConfig";

function PostView({ post, user, setEditing }) {
  const navigate = useNavigate();
  const isAuthor = user && user === post.authorName;

  const handleDelete = async () => {
    if (!window.confirm("정말삭제 하시겠습니까?")) {
      return;
    }
    try {
      await api.delete(`/api/board/${post.id}`);
      alert("게시글 삭제 성공!");
      navigate("/board");
    } catch (err) {
      console.error(err);
      if (err.response.status === 403) {
        alert("삭제 권한이 없습니다.");
      } else {
        alert("삭제 실패");
      }
    }
  };

  //날짜 format함수 -> 날짜, 시간 출력
  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleString();
  };

  return (
    <>
      <div className="detail-container">
        <h2 className="post-title">{post.title}</h2>
        <div className="post-meta">
          <span className="author">작성자: {post.authorName}</span>
          <span className="date">작성일: {formatDate(post.createDate)}</span>
          <span className="views">조회수👀: {post.viewCount || 0}</span>
        </div>
        <div className="post-content">{post.content}</div>
      </div>

      <div className="button-group">
        <button className="list-button" onClick={() => navigate("/board")}>
          글목록
        </button>
        {isAuthor && (
          <>
            <button className="edit-button" onClick={() => setEditing(true)}>
              수정
            </button>
            <button className="delete-button" onClick={handleDelete}>
              삭제
            </button>
          </>
        )}
      </div>
    </>
  );
}
export default PostView;
