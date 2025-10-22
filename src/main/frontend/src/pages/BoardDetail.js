import "./BoardDetail.css";
import api from "../api/axiosConfig";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import PostView from "../component/PostView";
import PostEdit from "../component/PostEdit";
import CommentForm from "../component/CommentForm";
import CommentList from "../component/CommentList";

function BoardDetail({ user }) {
  //user(props) -> 현재 로그인한 사용자의 username

  const [post, setPost] = useState(null); //해당 글id로 요청한 글 객체
  const [loading, setLoading] = useState(true);
  const [editing, setEditing] = useState(false); // 수정화면 출력 여분
  const [error, setError] = useState(null);

  const { id } = useParams(); //id 파라미터 받아오기

  //클릭한 글의 id로 글 (1개)가져오기
  const loadPost = async () => {
    try {
      setLoading(true);
      const res = await api.get(`/api/board/${id}`);
      setPost(res.data);
    } catch (err) {
      console.error(err);
      setError("해당 게시글은 존재하지 않습니다.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadPost(); //게시글 다시 불러오기
    loadComments();
  }, [id]);

  //댓글 관련 이벤트 처리

  //기존 댓글 배열
  const [comments, setComments] = useState([]);

  //댓글 리스트 불러오기 함수
  const loadComments = async () => {
    try {
      const res = await api.get(`/api/comments/${id}`);
      setComments(res.data);
    } catch (err) {
      console.error(err);
      alert("댓글 리스트 불러오기 실패");
    }
  };

  if (loading) return <p>게시글 로딩 중...</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;
  if (!post)
    return <p style={{ color: "red" }}>해당 게시글이 존재하지 않습니다.</p>;

  return (
    <div className="detail-container">
      {/* 게시글 영역 */}
      {editing ? (
        <PostEdit post={post} setEditing={setEditing} setPost={setPost} />
      ) : (
        <PostView post={post} user={user} setEditing={setEditing} />
      )}

      {/* 댓글 영역 */}
      <div className="comment-section">
        <CommentForm user={user} boardId={id} loadComments={loadComments} />
        <CommentList
          comments={comments}
          user={user}
          loadComments={loadComments}
        />
      </div>
    </div>
  );
}

export default BoardDetail;
