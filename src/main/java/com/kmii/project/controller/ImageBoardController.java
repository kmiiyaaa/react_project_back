package com.kmii.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kmii.project.dto.ImageBoardDto;
import com.kmii.project.entity.ImageBoard;
import com.kmii.project.repository.ImageBoardRepository;
import com.kmii.project.repository.UserRepository;

@RestController
@RequestMapping("/api/image-board")
public class ImageBoardController {

	@Autowired
	private ImageBoardRepository imageBoardRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	//게시글 페이징 처리(게시글 리스트)
	@GetMapping
	public ResponseEntity<?> pagingList(@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(name="size", defaultValue = "10")int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		Page<ImageBoard> boardPage = imageBoardRepository.findAll(pageable);
	
		Map<String, Object> pagingResponse = new HashMap<>();
	    List<ImageBoardDto> dtoList = boardPage.getContent().stream()
	                                           .map(b -> new ImageBoardDto(b, false))
	                                           .toList();
	
		pagingResponse.put("posts", dtoList);
		pagingResponse.put("currentPage", boardPage.getNumber());
		pagingResponse.put("totalPages", boardPage.getTotalPages());
		pagingResponse.put("totalItems", boardPage.getTotalElements());
	
		return ResponseEntity.ok(pagingResponse);		
			
		}
	
	//게시글 작성

	
	
}
