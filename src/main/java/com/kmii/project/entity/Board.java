package com.kmii.project.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;




@Entity
@Getter
@Setter
public class Board {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 게시판 번호
	
	private String title;  // 게시판 글 제목
	private String content; // 게시판 글 내용
	
	@CreationTimestamp 
	private LocalDateTime createDate; // 게시판 글 작성날짜

	
	@ManyToOne(fetch = FetchType.LAZY) // 게시글 : 작성자 = N:1
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private SiteUser author; // 게시판 작성자
	
	private int viewCount=0; //조회수 
	
	private String imageUrl;// 이미지 url
	
	
	@OneToMany(mappedBy = "board")
	private List<Comment> comment = new ArrayList<>();
	
	
	

}
