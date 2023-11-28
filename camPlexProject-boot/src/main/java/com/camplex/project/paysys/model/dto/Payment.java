package com.camplex.project.paysys.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class Payment {
	
	private int paymentNo;
	private int memberNo;
	private int campNo;
	private int campDeNo;
	private int itemNo;
	private int campPayment;
	private int rentalPayment;
	private int amountPayment;
	private String paymentType;
	private String paymentDate;
	private String checkInDate;
	private String checkOutDate;
	private String paymentStatusFlag;
	
}
