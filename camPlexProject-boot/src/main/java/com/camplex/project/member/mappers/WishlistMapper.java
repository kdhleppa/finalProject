package com.camplex.project.member.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
<<<<<<< HEAD
<<<<<<< HEAD
import org.apache.ibatis.annotations.Param;
=======
>>>>>>> 5f8b14e2d1ab1ec344e02412f234a09b3bd964f9
=======
>>>>>>> 5f8b14e2d1ab1ec344e02412f234a09b3bd964f9

import com.camplex.project.camping.model.dto.Camp;

@Mapper
public interface WishlistMapper {

	public int wishlistInsert(@Param("memberNo") int memberNo, @Param("itemNo") int itemNo);

	public int searchWishlist(@Param("memberNo") int memberNo, @Param("itemNo") int itemNo);

	public List<Camp> selectCampWish(int memberNo);

	public void deleteCampWish(@Param("memberNo")int memberNo, @Param("campNo")int campNo);



}
