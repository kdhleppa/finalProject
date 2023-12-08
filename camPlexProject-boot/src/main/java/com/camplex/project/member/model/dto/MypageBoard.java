package com.camplex.project.member.model.dto;

import java.util.List;

import com.camplex.project.board.model.dto.Board;
import com.camplex.project.paysys.model.dto.Reservations;
import com.camplex.project.qna.model.dto.Qna;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Setter
@Getter
public class MypageBoard {

	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private int boardReadCount;
	private String boardType;
	private String boardCreateDate;
	private int likeCount;

	// 서브쿼리
	private int commentCount; // 댓글 수
}
