package com.camplex.project.member.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CEOMember {
	
	// 등업신청 시 정보 DTO
	
	private int memberNo;
	private String campName; // 캠핑장 명
	private String campAdress; // 캠핑장 주소
	private String ceoName; // 대표 이름
	private String businessLicense; // 사업자 등록 번호
	private String tourLicense; // 관광사업 등록증
	private String accountHolder; // 예금주
	private String bankName; // 은행명
	private String ceoAccount; // 사장 입금 계좌

}
