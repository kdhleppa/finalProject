package com.camplex.project.member.model.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class Wishlist {
	
	private int wishlistNo;
	private int memberNo;
	private int campNo;
	private int itemNo;

}
