package com.camplex.project.paysys.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.camplex.project.paysys.mappers.PaysysMapper;
import com.camplex.project.paysys.model.dto.CartItem;
import com.camplex.project.paysys.model.dto.InfoForReservation;
import com.camplex.project.paysys.model.dto.RentalPaymentItem;
import com.camplex.project.paysys.model.dto.rentPayList;

@Service
@PropertySource("classpath:/config.properties")
public class PaysysServiceImpl implements PaysysService{

	@Autowired
	private PaysysMapper mapper;
	
	/** 회원카트 없는사람 생성
	 *
	 */
	@Transactional
	@Override
	public void createCart(int memberNo) {
		mapper.createCart(memberNo);
	}

	/** 카트에 아이템 넣기
	 *
	 */
	@Transactional
	@Override
	public int insertCart(Map<String, Object> map) {
		return mapper.insertCart(map);
	}


	/** 이미 있는 아이템인지 비교
	 *
	 */
	@Override
	public int searchCartItem(Map<String, Object> map) {
		return mapper.searchCartItem(map);
	}

	/** 카트가있다면 몇번인지 검색
	 *
	 */
	@Override
	public Integer searchMembersCartNo(int memberNo) {
		return mapper.searchMembersCartNo(memberNo);
	}

	/** 카트 수량 변경
	 *
	 */
	@Transactional
	@Override
	public int quantityUpdateCart(CartItem cartItem) {
		return mapper.quantityUpdateCart(cartItem);
	}

		
	

	/** 아이템 캠핑예약만 변경
	 *
	 */
	@Transactional
	@Override
	public int moveItemSite(Map<String, Object> map) {
		return mapper.moveItemSite(map);
	}

	/** 존재여부 검사
	 *
	 */
	@Override
	public int searchResult(Map<String, Object> map) {
		return mapper.searchResult(map);
	}

	@Transactional
	@Override
	public void deleteItemcart(Map<String, Object> map) {
		mapper.deleteItemcart(map);
	}

	@Transactional
	@Override
	public int deleteCart(int cartItemNo) {
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

	@Transactional
	@Override
	public int deleteAllCart(int memberNo) {
		return mapper.deleteAllCart(memberNo);
	}

	

	@Override
	public rentPayList selectCheckCart(Integer cartItemNo, int memberNo) {
		return mapper.selectCheckCart(cartItemNo, memberNo);
	}

	@Transactional
	@Override
	public int insertCartNoQuantity(Map<String, Object> map) {
		return mapper.insertCartNoQuantity(map);
	}

	@Transactional
	@Override
	public int insertPayRental(Map<String, Object> map) {
		return mapper.insertPayRental(map);
	}

	@Transactional
	@Override
	public int insertPayRentalItem(RentalPaymentItem rent) {
		return mapper.insertPayRentalItem(rent);
	}

	@Override
	public Integer selectLastInsertId() {
		return mapper.selectLastInsertId();
	}

	
	/** 카카오용 페이먼트 렌탈 입력
	 *
	 */
	@Transactional
	@Override
	public int insertPayRentalKakao(Map<String, Object> map) {
		return mapper.insertPayRentalKakao(map);
	}


	


	



}
