package com.camplex.project.item.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Setter
@Getter
public class MembersReservationDate {
	public int reservationNo;
	public String campName;
	public String campEntDate;
	public String campOutDate;

}
