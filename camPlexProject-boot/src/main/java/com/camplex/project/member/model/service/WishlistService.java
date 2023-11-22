package com.camplex.project.member.model.service;

import java.util.List;

import com.camplex.project.camping.model.dto.Camp;
import com.camplex.project.item.model.dto.Item;

public interface WishlistService {

	int wishlistInsert(int memberNo, int itemNo);

	int searchWishlist(int memberNo, int itemNo);

	List<Camp> selectCampWish(int memberNo);

	
}
