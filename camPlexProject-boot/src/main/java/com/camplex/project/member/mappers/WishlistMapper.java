package com.camplex.project.member.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

<<<<<<< HEAD
=======
import org.apache.ibatis.annotations.Param;




>>>>>>> 2b377711fb51cab7dcec1defbd5b2894d556c735
import com.camplex.project.camping.model.dto.Camp;

@Mapper
public interface WishlistMapper {

	public int wishlistInsert(@Param("memberNo") int memberNo, @Param("itemNo") int itemNo);

	public int searchWishlist(@Param("memberNo") int memberNo, @Param("itemNo") int itemNo);

	public List<Camp> selectCampWish(int memberNo);

	public void deleteCampWish(@Param("memberNo")int memberNo, @Param("campNo")int campNo);



}
