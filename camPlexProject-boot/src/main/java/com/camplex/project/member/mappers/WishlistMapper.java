package com.camplex.project.member.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.camplex.project.camping.model.dto.Camp;

@Mapper
public interface WishlistMapper {

	int wishlistInsert(int memberNo, int itemNo);

	int searchWishlist(int memberNo, int itemNo);

	List<Camp> selectCampWish(int memberNo);

}
