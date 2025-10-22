import { useParams } from "react-router-dom";
import api from "../api/axiosConfig";
import "./ImageBoardDetail.css";
import { useEffect, useState } from "react";

function ImageBoardDetail() {
  const { id } = useParams();
  const [post, setPost] = useState(null);

  useEffect(() => {
    fetchPost();
  }, [id]);

  const fetchPost = async () => {
    try {
      const res = await api.get(`/${id}`);
      setPost(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  if (!post) return <p>Loading...</p>;

  return (
    <div className="post-detail">
      <h2>{post.title}</h2>
      <p>작성자: {post.authorName}</p>
      <p>작성일: {new Date(post.createDate).toLocaleString()}</p>
      <img src={post.imageUrl} alt={post.title} className="post-image" />
      <p>{post.content}</p>
    </div>
  );
}
export default ImageBoardDetail;
