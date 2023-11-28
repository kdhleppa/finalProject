package com.camplex.project.item.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FindCartItem {

	private int itemNo;
	private String itemType;
	private int inStoreCount;
	private int loanCount;
	private int itemPrice;
	private String itemName;
	private String itemDescription;
	private String itemStatus;
	private int discountRate;
	private int reservationNo;
	private int cartItemNo;
	private int itemQuantity;
	private String thumbnail;
	
	private List<ItemImg> imageList;
	
}
