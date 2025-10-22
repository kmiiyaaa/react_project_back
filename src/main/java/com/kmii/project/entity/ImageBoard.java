package com.kmii.project.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ImageBoard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 게시판 번호
	
	private String title;  // 게시판 글 제목
	private String content; // 게시판 글 내용
	
	@CreationTimestamp 
	private LocalDateTime createDate; // 게시판 글 작성날짜

	
	@ManyToOne // 게시글 : 작성자 = N:1
	private SiteUser author; // 게시판 작성자
	
	private int viewCount=0; //조회수 
	
	private String imageUrl;// 이미지 url
	

}
