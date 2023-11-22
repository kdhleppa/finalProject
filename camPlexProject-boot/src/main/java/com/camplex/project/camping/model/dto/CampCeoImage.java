package com.camplex.project.camping.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CampCeoImage {

	private int campCeoImageNo;
	private int campNo;
	private String campCeoImagePath;
	private String campCeoImageReName;
	private String campCeoImageOriginal;
	private int campCeoImageOrder;
	
}
