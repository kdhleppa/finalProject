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
public class Camp {

	private int campNo;
	private int memberNo;
	private String campAddress;
	private String campPhone;
	private String campName;
	private String campOption;
	private String campAroundView;
	private String campMap;
	private String mannerTime;
	private String category;
	private char campDeleteFlag;
	private String campInfo;
	
	private List<CampDetail> campDetailList;
}