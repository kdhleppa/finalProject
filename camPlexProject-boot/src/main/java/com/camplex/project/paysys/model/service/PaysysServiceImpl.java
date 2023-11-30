package com.camplex.project.paysys.model.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.camplex.project.paysys.mappers.PaysysMapper;
import com.camplex.project.paysys.model.dto.CartItem;
import com.camplex.project.paysys.model.dto.InfoForReservation;

@Service
@PropertySource("classpath:/config.properties")
public class PaysysServiceImpl implements PaysysService{

	@Autowired
	private PaysysMapper mapper;
	
	@Transactional
	@Override
	public void createCart(int memberNo) {
		mapper.createCart(memberNo);
	}

	@Transactional
	@Override
	public int insertCart(Map<String, Object> map) {
		return mapper.insertCart(map);
	}


	@Override
	public int searchCartItem(Map<String, Object> map) {
		return mapper.searchCartItem(map);
	}

	@Override
	public Integer searchMembersCartNo(int memberNo) {
		return mapper.searchMembersCartNo(memberNo);
	}

	@Transactional
	@Override
	public int quantityUpdateCart(CartItem cartItem) {
		return mapper.quantityUpdateCart(cartItem);
	}

	@Override
	public int moveItemSite(CartItem cartItem) {
		return mapper.moveItemSite(cartItem);
	}

	/** 캠핑 결제 내역 저장
	 *
	 */
	@Override
	public int insertPayCamp(InfoForReservation info) {
		return mapper.insertPayCamp(info);
	}


}
