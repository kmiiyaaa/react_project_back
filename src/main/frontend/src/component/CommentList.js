import { useState } from "react";
import api from "../api/axiosConfig";

function CommentList({ comments, user, loadComments }) {
  // comments : 댓글 리스트

  const [editCommentId, setEditCommentId] = useState(null);
  const [editCommentContent, setEditCommentContent] = useState("");

  //댓글 삭제 함수
  const handleCommentDelete = async (commentId) => {
    if (!window.confirm("정말 삭제하시겠습니까?")) {
      return;
    }

    try {
      await api.delete(`/api/comments/${commentId}`);
      alert("댓글 삭제 성공");
      loadComments(); // 갱신된 댓글 리스트 다시 로딩
    } catch (err) {
      console.error(err);
      alert("삭제 권한이 없거나 삭제할 수 없는 댓글입니다.");
    }
  };

  //댓글 수정 이벤트 함수
  const handleCommentUpdate = async (commentId) => {
    try {
      await api.put(`/api/comments/${commentId}`, {
        content: editCommentContent, //content -> 수정내용
      });
      setEditCommentId(null);
      setEditCommentContent("");
      loadComments();
    } catch (err) {
      alert("댓글 수정 실패!");
    }
  };

  //댓글 수정여부 확인
  const handleCommentEdit = (comment) => {
    setEditCommentId(Number(comment.id)); // 숫자로 통일
    setEditCommentContent(comment.content);
  };

  // 댓글 날짜 함수 - 날짜,시간 출력
  const commentFormDate = (dateString) => {
    return new Date(dateString).toLocaleString();
  };

  return (
    <ul className="comment-list">
      {comments.length === 0 && <p>등록 댓글이 없습니다.</p>}
      {comments.map((c) => (
        <li key={c.id} className="comment-item">
          <div className="comment-header">
            <span className="comment-author">{c.author.username}</span>
            <span className="comment-date">
              {commentFormDate(c.createDate)}
            </span>
          </div>

          {editCommentId === c.id ? (
            /* 댓글 수정 섹션 */
            <>
              <textarea
                value={editCommentContent}
                onChange={(e) => setEditCommentContent(e.target.value)}
              />
              <>
                <div className="edit-button-group">
                  <button
                    className="comment-save"
                    onClick={() => handleCommentUpdate(c.id)}
                  >
                    저장
                  </button>
                  <button
                    className="comment-cancel"
                    onClick={() => setEditCommentId(null)}
                  >
                    취소
                  </button>
                </div>
              </>
            </>
          ) : (
            /* 댓글 읽기 섹션 */
            <>
              <div className="comment-content">{c.content}</div>
              <div className="button-group">
                {user === c.author.username && (
                  <div>
                    <button
                      className="edit-button"
                      onClick={() => handleCommentEdit(c)}
                    >
                      수정
                    </button>
                    <button
                      className="delete-button"
                      onClick={() => handleCommentDelete(c.id)}
                    >
                      삭제
                    </button>
                  </div>
                )}
              </div>
            </>
          )}
        </li>
      ))}
    </ul>
  );
}

export default CommentList;
