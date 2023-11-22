package com.camplex.project.member.mappers;

import java.util.List;

import com.camplex.project.camping.model.dto.Camp;

public interface WishlistMapper {

	int wishlistInsert(int memberNo, int itemNo);

	int searchWishlist(int memberNo, int itemNo);

	List<Camp> selectCampWish(int memberNo);

}
