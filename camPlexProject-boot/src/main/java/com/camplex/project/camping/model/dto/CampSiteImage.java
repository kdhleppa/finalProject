package com.camplex.project.camping.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CampSiteImage {

	private int campImageNo;
	private int campNo;
	private String campImagePath;
	private String campImageReName;
	private String campImageOriginal;
	private int campImageOrder;
	
}
