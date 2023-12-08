package com.camplex.project.paysys.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class Reservations {
	
	private int reservationNo;
	private int paymentNo;
	private int memberNo;
	private int campNo;
	private int campDeNo;
	private String campEntDate;
	private String campOutDate;
	private int adultCount;
	private int kidCount;
	private String demand;
	
	private String campName;
	private String campDeName;
	private String thumbnail;
	
	
}
