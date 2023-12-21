package com.camplex.project.qna.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Qna {
	private int QNANo;
	private String QNATitle;
	private String QNAContent;
	private String QNAAnswer;
	private String answerFlag;
	private String QNACreateDate;
	
	private int memberNo;
	private String memberNickname;
	
}
