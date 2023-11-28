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
public class CampDetail {

	private int campDeNo;
	private int campNo;
	private String campDeName;
	private int campDePrice;
	private int capacity;
	private int fullCapacity;
	private String checkIn;
	private String checkOut;
	
	
	private String campDeThumbnail;
	
	private List<CampDetailImage> campDetailImageList;
	
	private String campName;
	private String mannerTime;
	private String category;
}
