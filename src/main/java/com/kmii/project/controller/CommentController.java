package com.kmii.project.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kmii.project.dto.CommentDto;
import com.kmii.project.entity.Board;
import com.kmii.project.entity.Comment;
import com.kmii.project.entity.SiteUser;
import com.kmii.project.repository.BoardRepository;
import com.kmii.project.repository.CommentRepository;
import com.kmii.project.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final BoardController boardController;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BoardRepository boardRepository;


    CommentController(BoardController boardController) {
        this.boardController = boardController;
    }
	
	
	//댓글 작성
	@PostMapping("{boardId}")
	public ResponseEntity<?> commentWrite(
			@PathVariable("boardId") Long boardId,
			@Valid @RequestBody CommentDto commentDto,
			BindingResult bindingResult,
			Authentication auth) {
		
		//게시글 존재 여부 확인
		Optional<Board> _board = boardRepository.findById(boardId);
		if(_board.isEmpty()) {
			Map<String, String> error = new HashMap<>();
			error.put("boardError", "해당 게시글이 존재하지 않습니다.");
			return ResponseEntity.status(404).body(error);
		}
		
		//로그인한 유저의 존재 가져오기
		SiteUser user = userRepository.findByUsername(auth.getName()).orElseThrow();
		
		Comment comment = new Comment();
		comment.setBoard(_board.get());
		comment.setAuthor(user);
		comment.setContnet(commentDto.getContent());
		
		commentRepository.save(comment);
		
		return ResponseEntity.ok(comment);
		
	}

}
