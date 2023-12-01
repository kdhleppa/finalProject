package com.camplex.project.qna.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ceoQna {
	private int ceoQnaNo;
	private String ceoQnaTitle;
	private String ceoQnaContent;
	private String ceoQnaAnswer;
	private String ceoAnswerFlag;
	private String ceoQnaCreateDate;
	
	private int memberNo;
	private String memberNickname;
	
	private int campNo;
}
