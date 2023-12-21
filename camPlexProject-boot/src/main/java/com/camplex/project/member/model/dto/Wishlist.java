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
	
	private int wishListNo;
	private int memberNo;
	private int campNo;
	private int itemNo;

	private String thumbnail;
	private String itemName;
	private String campName;
	private String campThumbnail;
}
