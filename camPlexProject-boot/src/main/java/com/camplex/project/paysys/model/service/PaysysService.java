package com.camplex.project.paysys.model.service;

import java.util.Map;

import com.camplex.project.paysys.model.dto.CartItem;
import com.camplex.project.paysys.model.dto.InfoForReservation;

public interface PaysysService {

	void createCart(int memberNo);

	int insertCart(Map<String, Object> map);

	int searchCartItem(Map<String, Object> map);

	Integer searchMembersCartNo(int memberNo);

	int quantityUpdateCart(CartItem cartItem);

	/** 캠핑 결제 내역 저장
	 * @param info
	 * @return
	 */
	int insertPayCamp(InfoForReservation info);



	int moveItemSite(Map<String, Object> map);



	int searchResult(Map<String, Object> map);



	void deleteItemcart(Map<String, Object> map);



	int deleteCart(int cartItemNo);









}
