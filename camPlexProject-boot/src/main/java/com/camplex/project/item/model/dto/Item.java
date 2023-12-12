package com.camplex.project.item.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class Item {
	
	
	private int itemNo;
	private String itemType;
	private int inStoreCount;
	private int loanCount;
	private int itemPrice;
	private String itemName;
	private String itemDescription;
	private String itemStatus;
	private int discountRate;
	
	private String thumbnail;
	private String itemDeImg;
	
	private List<ItemImg> imageList;
	
}
