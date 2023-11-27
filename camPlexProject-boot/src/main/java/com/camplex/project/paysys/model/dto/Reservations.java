package com.camplex.project.paysys.model.dto;

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
	

}
