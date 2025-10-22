package com.kmii.project.dto;

import java.time.LocalDateTime;

import com.kmii.project.entity.Board;
import com.kmii.project.entity.ImageBoard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageBoardDto {
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private LocalDateTime createDate;
    private String imageUrl;

    public ImageBoardDto(ImageBoard board, boolean isDetail) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.authorName = board.getAuthor().getUsername();
        this.imageUrl = board.getImageUrl();

        if(isDetail) {
            this.content = board.getContent();
            this.createDate = board.getCreateDate();
        }
    }
}
