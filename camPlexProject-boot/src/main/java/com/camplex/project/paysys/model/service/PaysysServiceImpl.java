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

		
	

	@Transactional
	@Override
	public int moveItemSite(Map<String, Object> map) {
		return mapper.moveItemSite(map);
	}

	@Override
	public int searchResult(Map<String, Object> map) {
		return mapper.searchResult(map);
	}

	@Transactional
	@Override
	public void deleteItemcart(Map<String, Object> map) {
		mapper.deleteItemcart(map);
	}

	@Override
	public int deleteCart(int cartItemNo) {
		// TODO Auto-generated method stub
		return mapper.deleteCart(cartItemNo);
	}

	/** 캠핑 결제 내역 저장
	 *
	 */
	@Override
	public int insertPayCamp(InfoForReservation info) {
		return mapper.insertPayCamp(info);
	}

	/** 캠핑 카카오 결제 내역 저장
	 *
	 */
	@Override
	public int insertKakao(InfoForReservation info) {
		return mapper.insertKakao(info);
	}

	/** 캠핑 카카오 결제 내역 결제완료로 변경
	 *
	 */
	@Override
	public int updatePayState(int tempPaymentNo) {
		return mapper.updatePayState(tempPaymentNo);
	}

	/** 결제 취소시 내역 삭제(카카오)
	 *
	 */
	@Override
	public int deleteKakao(int tempPaymentNo) {
		return mapper.deleteKakao(tempPaymentNo);
	}

	/** 결제 완료시 예약 테이블 저장(카카오)
	 *
	 */
	@Override
	public int insertReservation(InfoForReservation tempInfo) {
		return mapper.insertReservation(tempInfo);
	}


	



}
