import "./ImageBoard.css";
import api from "../api/axiosConfig";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function ImageBoard({ user }) {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [totalItems, setTotalItems] = useState(0);
  const navigate = useNavigate();

  //게시글 불러오기

  const loadPost = async (page = 0) => {
    try {
      setLoading(true);
      const res = await api.get(`/api/image-board?page=${page}&size=10`);
      console.log("📦 API 응답 데이터:", res.data);

      setPosts(res.data.posts); // 전체 게시글 -> 개시글의 배열
      setCurrentPage(res.data.currentPage); //현재 페이지 번호
      setTotalPages(res.data.totalPages); // 전체 페이지 수
      setTotalItems(res.data.totalItems); //모든 글의 갯수
    } catch (err) {
      console.error(err);
      setError("게시글을 불러오는데 실패하였습니다.");
    } finally {
      setLoading(false);
    }
  };

  // 글쓰기 버튼 클릭
  const handleWrite = () => {
    if (!user) {
      alert("로그인 후 글 작성이 가능합니다.");
      return;
    }
    navigate("/image-board/write");
  };

  // 게시글 상세보기
  const handleDetail = (id) => {
    navigate(`/image-board/${id}`);
  };

  useEffect(() => {
    loadPost(currentPage);
  }, [currentPage]);

  //페이지 번호 그룹 배열 반환 함수(10개까지만)
  const getPageNumbers = () => {
    const startPage = Math.floor(currentPage / 10) * 10;
    const endPage = startPage + 10 > totalPages ? totalPages : startPage + 10;

    const pages = [];
    for (let i = startPage; i < endPage; i++) {
      pages.push(i);
    }

    return pages;
  };

  const imageDetail = (id) => {
    navigate(`/board/${id}`); // 상세 페이지 라우팅
  };

  return (
    <div className="image-board-container">
      <h2>🍽️ 맛집 포스팅</h2>

      {loading && <p>게시글을 불러오는 중입니다...</p>}
      {error && <p className="error">{error}</p>}

      <div className="image-grid">
        {posts.map((post) => (
          <div
            key={post.id}
            className="image-card"
            onClick={() => handleDetail(post.id)}
          >
            <img
              src={post.imageUrl || "/default-thumbnail.jpg"}
              alt={post.title}
            />
            <h4>{post.title}</h4>
          </div>
        ))}
      </div>

      {/* 페이지네이션 */}
      <div className="pagination">
        <button
          onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 0))}
          disabled={currentPage === 0}
        >
          이전
        </button>
        {/* 페이지 번호 그룹 10개씩 출력 */}
        {getPageNumbers().map((num) => (
          <button
            className={num === currentPage ? "active" : ""}
            key={num}
            onClick={() => setCurrentPage(num)}
          >
            {num + 1}
          </button>
        ))}
        <button
          onClick={() =>
            setCurrentPage((prev) => Math.min(prev + 1, totalPages - 1))
          }
          disabled={currentPage + 1 === totalPages}
        >
          다음
        </button>
      </div>

      <div className="write-btn-wrap">
        <button className="write-btn" onClick={handleWrite}>
          글쓰기
        </button>
      </div>
    </div>
  );
}

export default ImageBoard;
