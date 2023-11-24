package com.camplex.project.paysys.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Setter
@Getter
public class CartItem {
	
	private int cartItemNo;
	private int cartNo;
	private int itemNo;
	private int reservationNo;
	private int itemQuantity;

}
