import { useState } from "react";
import api from "../api/axiosConfig";

function CommentForm({ user, loadComments, boardId }) {
  const [newComment, setNewComment] = useState("");
  const [commentErrors, setCommentErrors] = useState({});

   //댓글 쓰기 함수
  const handleCommentSubmit = async (e) => {
    e.preventDefault(); //초기화
    setCommentErrors({});

    if (!user) {
      alert("로그인 후 댓글 작성해주세요");

      return; // 안써주면 로그아웃 상태에서 댓글작성시 밑에 내용도 알람창 뜬다.
    }
    if (!newComment) {
      alert("댓글 내용을 입력해주세요.");
      return;
    }

    try {
      alert("댓글을 입력하시겠습니까?"); // 원래는 confirm 사용해야함
      await api.post(`/api/comments/${boardId}`, { content: newComment }); //여기서 boardId는 게시글의 id
      setNewComment("");

      //댓글 리스트 불러오기 호출
      loadComments(); //새 댓글 기존 댓글 리스트에 반영
    } catch (err) {
      if (err.response && err.response.status === 400) {
        setCommentErrors(err.response.data);
      } else {
        console.error(err);
        alert("댓글 등록 실패!");
      }
    }
  };

  return (
    <div className="comment-form">
      <h3>댓글 쓰기</h3>
      <form onSubmit={handleCommentSubmit}>
        <textarea
          placeholder="댓글을 입력하세요"
          value={newComment}
          onChange={(e) => setNewComment(e.target.value)}
        ></textarea>
        {commentErrors.content && (
          <p style={{ color: "red" }}>{commentErrors.content}</p>
        )}
         <button type="submit" className="list-button">등록</button>
      </form>
    </div>
  );
}

export default CommentForm;
