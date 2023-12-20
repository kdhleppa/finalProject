package com.camplex.project.paysys.model.dto;

import java.util.Date;
import java.util.List;

import com.camplex.project.member.model.dto.ItemInfoMypage;

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
	private Date campEntDate;
	private String campEntDate2;
	private String campOutDate;
	private int adultCount;
	private int kidCount;
	private String demand;
	
	private String campName;
	private String campDeName;
	private String thumbnail;
	
	private int campPayment;
	private String paymentType;
	private String paymentStatusFlag;
	private String bankAccount;

	private String campAddress;
	
	private List<ItemInfoMypage> itemList;
	
	private int totalPrice;
}
