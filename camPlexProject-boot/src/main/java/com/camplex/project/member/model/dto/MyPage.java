package com.camplex.project.member.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Setter
@Getter
public class MyPage {

	private int memberNo;
	
	private String campImg; // 캠핑장 이미지
	private String campName; // 캠핑장 이름
	private String rentalName; // 렌탈 상품명
	private String paymentInfo; // 결제 정보
	private String paymentState; // 결제 상태
	private String wishItemImg; // 관심 상품 이미지
	private String wishItemName; // 관심 상품명
	private String QnATitle; // 문의사항 제목
	private String QnAState; // 문의사항 답변 상태
	private String communityState; // 커뮤니티 제목
	private String communityCommentCount; // 커뮤니티 댓글 수
	
}
