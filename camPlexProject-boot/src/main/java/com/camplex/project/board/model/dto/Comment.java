package com.camplex.project.board.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Comment {
    private int commentNo;
    private String commentContent;
    private String commentCreateDate;
    private int boardNo;
    private int memberNo;
    private String commentDeleteFlag;
    private int parentNo;
    private String memberNickname;
    private String profileImage;
}
