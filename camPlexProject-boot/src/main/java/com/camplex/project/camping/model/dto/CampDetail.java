package com.camplex.project.camping.model.dto;

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
}
