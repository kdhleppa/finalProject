package com.camplex.project.member.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;

import com.camplex.project.camping.model.dto.Camp;
import com.camplex.project.item.model.dto.Item;

@Mapper
public interface WishlistMapper {

	public int wishlistInsert(@Param("memberNo") int memberNo, @Param("itemNo") int itemNo);

	public int searchWishlist(@Param("memberNo") int memberNo, @Param("itemNo") int itemNo);

	public List<Camp> selectCampWish(int memberNo);

	public void deleteCampWish(@Param("memberNo") int memberNo, @Param("campNo") int campNo);

	public void deleteItemWish(@Param("memberNo") int memberNo, @Param("itemNo") int itemNo);

	public int wishDelete(Map<String, Object> map);




}
