package com.kmii.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kmii.project.entity.Board;
import com.kmii.project.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	//댓글이 달린 게시글로 댓글리스트 반환 메서드
	List<Comment> findByBoard(Board board);

}
