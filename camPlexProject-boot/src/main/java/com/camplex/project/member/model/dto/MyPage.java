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
public class MyPage {

	// 마이페이지 정보 뿌리기용 DTO
	
	private int memberNo;
	
	private List<Reservations> resList;
	private List<Wishlist> wishList;
	private List<Qna> qnaList;
	private List<Board> boardList;
	
	
}
