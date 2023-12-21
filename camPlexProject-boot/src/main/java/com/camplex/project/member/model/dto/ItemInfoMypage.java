package com.camplex.project.member.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class ItemInfoMypage {

	private int reservationNo;
	private int quantity;
	private int item_no;
	private String itemName;
	private int rentalPaymentNo;
	private int rentalPaymentTotal;
	private String rentalPaymentType;
	
	private String thumbnail;
	private int itemPrice;
	
	
}
