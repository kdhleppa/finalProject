package com.camplex.project.paysys.mappers;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.camplex.project.paysys.model.dto.CartItem;
import com.camplex.project.paysys.model.dto.InfoForReservation;

@Mapper
public interface PaysysMapper {

	// 조회후 카트가 없으면 생성
	void createCart(@Param("memberNo") int memberNo);

	

	int insertCart(Map<String, Object> map);

	int searchCartItem(Map<String, Object> map);

	int searchCartNo(@Param("memberNo") int memberNo);



	Integer searchMembersCartNo(int memberNo);



	int quantityUpdateCart(CartItem cartItem);



	int moveItemSite(Map<String, Object> map);



	int searchResult(Map<String, Object> map);



	void deleteItemcart(Map<String, Object> map);



	int deleteCart(@Param("cartItemNo") int cartItemNo);



	/** 캠핑 결제 내역 저장
	 * @param info
	 * @return
	 */
	int insertPayCamp(InfoForReservation info);




}
