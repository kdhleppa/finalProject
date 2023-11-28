package com.camplex.project.paysys.model.service;

import java.util.Map;

import com.camplex.project.paysys.model.dto.CartItem;

public interface PaysysService {

	void createCart(int memberNo);

	

	int insertCart(Map<String, Object> map);

	int searchCartItem(Map<String, Object> map);



	Integer searchMembersCartNo(int memberNo);



	int quantityUpdateCart(CartItem cartItem);





}
