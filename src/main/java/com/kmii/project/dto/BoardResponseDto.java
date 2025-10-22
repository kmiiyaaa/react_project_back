package com.kmii.project.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.kmii.project.entity.Board;
import com.kmii.project.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDto {
	
	private Long id;
    private String title;
    private String content;
    private String authorName;
    private LocalDateTime createDate;
    private int viewCount; //조회수
    private List<Comment> comments;
   

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.authorName = board.getAuthor().getUsername();
        this.createDate = board.getCreateDate();
        this.viewCount = board.getViewCount();
        this.comments = board.getComment();

    }
}