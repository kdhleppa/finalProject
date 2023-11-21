package com.camplex.project.item.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ItemImg {
	
	private int itemImgNo;
	private int itemNo;
	private String itemImgPath;
	private String itemImgOriginal;
	private int itemImgOrder;

}
