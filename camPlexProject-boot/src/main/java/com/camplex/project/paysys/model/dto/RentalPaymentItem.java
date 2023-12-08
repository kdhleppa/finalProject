package com.camplex.project.paysys.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RentalPaymentItem {

	private Integer rentalItemNo;
	private Integer rentalPaymentNo;
	private Integer itemNo;
	private Integer rentalItemQuantity;
	private Integer reservationNo;
	private Integer cartItemNo;
	
	
}
