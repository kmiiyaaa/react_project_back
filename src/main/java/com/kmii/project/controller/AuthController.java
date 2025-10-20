package com.kmii.project.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kmii.project.entity.SiteUser;
import com.kmii.project.repository.UserRepository;



@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/signup")  // insert → post
	public ResponseEntity<?> signup(@RequestBody SiteUser req) {
		
		
		//username 이미 존재하는지 확인
		if(userRepository.findByUsername(req.getUsername()).isPresent()) {
			return ResponseEntity.badRequest().body("이미 존재하는 사용자 입니다.");
		}
		
		SiteUser siteUser = new SiteUser();
		siteUser.setUsername(req.getUsername());
		siteUser.setPassword(req.getPassword());
		
		
		//비밀번호 암호화해서 엔티티에 다시 넣기
		req.setPassword(passwordEncoder.encode(req.getPassword()));
		userRepository.save(req);	
		
		
		return ResponseEntity.ok("회원가입 성공!");
		
		
	}
	
	@GetMapping("/me")
	public ResponseEntity<?> me(Authentication auth) {
		return ResponseEntity.ok(Map.of("username",auth.getName()));
	}
	
}
