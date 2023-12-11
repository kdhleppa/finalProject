package com.camplex.project.paysys.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RentalPayment {

	private Integer rentalPaymentNo;
	private int memberNo;
	private int rentalPaymentTotal;
	private String rentalPaymentDate;
	private String paymentStatusFl;
	private String rentalSenderName;
	private String rentalPaymentType;
	
	
	
}
