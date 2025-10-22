package com.kmii.project.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;  // 댓글 번호
	
	@Column(nullable = false, length = 300)
	private String content; // 댓글 내용
	
	@CreationTimestamp
	private LocalDateTime createDate; // 댓글 입력 날짜+시간

	@ManyToOne(fetch = FetchType.LAZY) // 댓글 : 작성자 = N:1 (작성자 한명이 댓글 여러개 작성 가능)
	//@JoinColumn(name="author id") // join되는 테이블의 외래키 이름 지정
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Hibernate 내부용 필드는 무시하고, 실제 유저 정보만 JSON으로 보내라
	private SiteUser author;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name="board id")
	@JsonIgnore
	private Board board;  //댓글에 달릴 원 게시글의 id
	
}
