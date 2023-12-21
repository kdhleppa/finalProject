package com.camplex.project.camping.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CampDetailImage {

	private int campDeImageNo;
	private int campDeNo;
	private String campDeImagePath;
	private String campDeImageReName;
	private String campDeImageOriginal;
	private int campDeImageOrder;
	
}
