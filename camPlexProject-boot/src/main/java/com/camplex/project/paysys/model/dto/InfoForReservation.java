package com.camplex.project.paysys.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Setter
@Getter
public class InfoForReservation {
	
	private String campDeName;
	private int campDeNo;
	private String campName;
	private int campNo;
	private String customerName;
	private String customerTel;
	private int adultCount;
	private int kidCount;
	private String additionalCount; 
	private String entDate;
	private String outDate;
	private String customerEmail;
	private String demand;
	private int stayDay;
	private int price;
	private int priceOneDay;
	private String payType;
	
	private int memberNo;
	
}
