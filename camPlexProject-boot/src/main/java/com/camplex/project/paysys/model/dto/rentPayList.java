package com.camplex.project.paysys.model.dto;

import java.util.List;

import com.camplex.project.item.model.dto.ItemImg;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class rentPayList {
	private int memberNo;
	private int itemNo;
	private int itemPrice;
	private String itemName;
	private String campName;
	private String thumbnail;
	private int cartItemNo;
	private int itemQuantity;
	private int reservationNo;
	private String campEntdate;
	private String campOutdate;
	private List<ItemImg> imageList;
}
