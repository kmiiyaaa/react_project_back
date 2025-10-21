package com.kmii.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kmii.project.dto.BoardDto;
import com.kmii.project.entity.Board;
import com.kmii.project.entity.SiteUser;
import com.kmii.project.repository.BoardRepository;
import com.kmii.project.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/board")
public class BoardController {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	//전체 게시글 조회
//	@GetMapping
//	public List<Board> list(){
//		return boardRepository.findAll();
//	}
	
	//게시글 페이징 처리
	@GetMapping
	public ResponseEntity<?> pagingList(@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(name="size", defaultValue = "10")int size) {
		
		if(page<0) {
			page=0;
		}
		
		if(size <=0) {
			size = 10;
		}
		
		 //Pageable 객체 생성 -> findAll에서 사용
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		Page<Board> boardPage = boardRepository.findAll(pageable); //DB에서 페이징된 게시글만 조회  
		
		Map<String, Object> pagingResponse = new HashMap<>();
		pagingResponse.put("posts", boardPage.getContent()); // 페이징된 현재 페이지에 해당하는 게시글 리스트
		pagingResponse.put("currentPage", boardPage.getNumber()); // 현재 페이지 번호
		pagingResponse.put("totalPage", boardPage.getTotalPages()); // 모든페이지의 수
		pagingResponse.put("totalItems", boardPage.getTotalElements()); // 모든 글 수 
		
		return ResponseEntity.ok(pagingResponse);
		
		
	}
	
	
	//게시글 작성
//	@PostMapping
//	public ResponseEntity<?> write(@RequestBody Board req, Authentication auth) {
//		
//		SiteUser siteUser = userRepository.findByUsername(auth.getName())
//				.orElseThrow(() -> new UsernameNotFoundException("사용자 없음"));
//		
//		Board board = new Board();
//		board.setTitle(req.getTitle());
//		board.setContent(req.getContent());
//		board.setAuthor(siteUser);
//		
//		boardRepository.save(board);
//		return ResponseEntity.ok(board);
//		
//	}

	
	//게시글 작성 유효성체크 추가
	@PostMapping
	public ResponseEntity<?> write(@Valid @RequestBody BoardDto boardDto, BindingResult bindingResult, Authentication auth) {
		
		//로그인 여부 확인
		if(auth == null) {
			return ResponseEntity.status(401).body("로그인 후 글 작성 가능");
		}
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			bindingResult.getFieldErrors().forEach(
					err -> {
						errors.put(err.getField(), err.getDefaultMessage());
					}
					);	
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);					
		}
		
		SiteUser siteUser = userRepository.findByUsername(auth.getName())
				.orElseThrow(()-> new UsernameNotFoundException("사용자 없음"));
		
		Board board = new Board();
		board.setTitle(boardDto.getTitle());
		board.setContent(boardDto.getContent());
		board.setAuthor(siteUser);
		
		boardRepository.save(board); // DB에 저장하는 부분
		return ResponseEntity.ok(board); // 클라이언트에게 응답 보내는 부분
		
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
	
	//게시글 수정
	@PutMapping("/{id}")
	public ResponseEntity<?> updatePost(@PathVariable("id") Long id,@RequestBody Board updateBoard ,Authentication auth){
		
		Optional<Board> _board = boardRepository.findById(id);
		
		if(_board.isEmpty()) {
			return ResponseEntity.status(404).body("해당 글이 존재하지 않습니다.");
		}
		
		if(auth == null || !auth.getName().equals(_board.get().getAuthor().getUsername())) {
			return ResponseEntity.status(403).body("수정 권한이 없습니다.");
		}
		
		Board oldPost = _board.get(); // 기존 게시글
		oldPost.setTitle(updateBoard.getTitle());
		oldPost.setContent(updateBoard.getContent());
		
		boardRepository.save(oldPost); //수정한 글 내용 저장 
		
		return ResponseEntity.ok(oldPost);
	}

}
