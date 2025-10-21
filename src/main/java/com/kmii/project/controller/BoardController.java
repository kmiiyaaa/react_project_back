package com.kmii.project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kmii.project.entity.Board;
import com.kmii.project.entity.SiteUser;
import com.kmii.project.repository.BoardRepository;
import com.kmii.project.repository.UserRepository;

@RestController
@RequestMapping("/api/board")
public class BoardController {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	//전체 게시글 조회
	@GetMapping
	public List<Board> list(){
		return boardRepository.findAll();
	}
	
	
	//게시글 작성
	@PostMapping
	public ResponseEntity<?> write(@RequestBody Board req, Authentication auth) {
		
		SiteUser siteUser = userRepository.findByUsername(auth.getName())
				.orElseThrow(() -> new UsernameNotFoundException("사용자 없음"));
		
		Board board = new Board();
		board.setTitle(req.getTitle());
		board.setContent(req.getContent());
		board.setAuthor(siteUser);
		
		boardRepository.save(board);
		return ResponseEntity.ok(board);
		
	}
	
	// 특정 게시글 번호(id)로 조회 (글 상세보기)
	@GetMapping("/{id}")
	public ResponseEntity<?> getPost(@PathVariable("id") Long id) {
		
		Optional<Board> _board = boardRepository.findById(id);
		if(_board.isPresent()) {
			return ResponseEntity.ok(_board.get()); // 해당 id글을 반환
		}  else {
			return ResponseEntity.status(404).body("해당 게시글은 존재하지 않습니다.");
		}
	}
		
		
	// 특정 게시글 삭제(삭제권한 : 로그인 후 본인글만 삭제 가능)	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePost(@PathVariable("id") Long id, Authentication auth) {
		
		Optional<Board> _board = boardRepository.findById(id);
		
		
		// 삭제할 글의 존재 여부 확인
		if(_board.isEmpty()) {
			return ResponseEntity.status(404).body("삭제할 글이 존재하지 않습니다");
		} 
		
		// 로그인한 유저의 삭제 권한 확인
		if(auth == null || !auth.getName().equals(_board.get().getAuthor().getUsername())) {
			return ResponseEntity.status(403).body("해당 글에 대한 삭제 권한이 없습니다.");
		}
		
		boardRepository.delete(_board.get());
		return ResponseEntity.ok("글 삭제 성공!");
				
	}
	

}
