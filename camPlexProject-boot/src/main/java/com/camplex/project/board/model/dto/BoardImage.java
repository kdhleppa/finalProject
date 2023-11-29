package com.camplex.project.board.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardImage {
	private int boardImageNo;
	private String boardImagePath;
	private String boardImageReName;
	private String boardImageOriginal;
	private int boardImageOrder;
	private int boardNo;

	private String boardThumbnail;
}
