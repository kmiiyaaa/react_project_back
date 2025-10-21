package com.kmii.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

	@NotBlank(message="아이디를 입력해주세요")
	@Size(min=5, message="댓글은 최소5글자 이상 입력해주세요")
	private String content;
	
	@NotBlank(message="비밀번호를 입력해주세요")
	@Size(min=5, message="비밀번호는 최소5글자 이상 입력해주세요")
	private String password;
	
}
