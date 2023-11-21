package com.camplex.project.camping.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class camp {

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
}
