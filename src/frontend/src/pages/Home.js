import "./Home.css";
import food1 from "../assets/images/food1.jpg";
import food2 from "../assets/images/food2.jpg";
import { useNavigate } from "react-router-dom";

function Home() {
  const navigate = useNavigate();

  const handleExploreClick = () => {
    navigate("/image-board");
  };

  return (
    <div className="home-container">
      <section className="hero-section">
        <h1>🍽️ 우리동네 숨은 맛집을 함께 찾아요!</h1>
        <p>
          이웃이 추천한 진짜 맛집, <br />
          지금 바로 함께 나눠보세요 👨‍👩‍👧‍👦
        </p>
        <button className="explore-btn" onClick={handleExploreClick}>
          🔍 맛집 둘러보기
        </button>
      </section>

      <section className="best-section">
        <h3>🍽️ 이번 주 인기 게시글</h3>
        <div className="best-list">
          <div className="best-card">
            <img src={food1} alt="맛집1" />
            <div className="info">
              <h4>청량리 뼈구이 맛집</h4>
              <p>반차내고 가야한다는 서울 5대 뼈구이 맛집</p>
            </div>
          </div>

          <div className="best-card">
            <img src={food2} alt="맛집2" />
            <div className="info">
              <h4>서울 방어 맛집 모음</h4>
              <p>올 겨울 방어 놓치지 마세요!</p>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
}
export default Home;
