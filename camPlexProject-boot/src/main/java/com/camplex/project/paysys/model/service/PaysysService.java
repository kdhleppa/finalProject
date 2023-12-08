package com.camplex.project.paysys.model.service;

import java.util.List;
import java.util.Map;

import com.camplex.project.paysys.model.dto.CartItem;
import com.camplex.project.paysys.model.dto.InfoForReservation;
import com.camplex.project.paysys.model.dto.RentalPayment;
import com.camplex.project.paysys.model.dto.RentalPaymentItem;
import com.camplex.project.paysys.model.dto.rentPayList;

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

	/** 카카오 결제 내역 저장
	 * @param info
	 * @return
	 */
	int insertKakao(InfoForReservation info);

	/** 결제 완료로 변경
	 * @param tempPaymentNo
	 * @return
	 */
	int updatePayState(int tempPaymentNo);

	/** 결제 취소시 내역 삭제
	 * @param tempPaymentNo
	 * @return
	 */
	int deleteKakao(int tempPaymentNo);

	/** 결제 완료시 예약 테이블 저장(카카오)
	 * @param tempInfo
	 * @return 
	 */
	int insertReservation(InfoForReservation tempInfo);

	int deleteAllCart(int memberNo);

	rentPayList selectCheckCart(Integer cartItemNo, int memberNo);

	int insertCartNoQuantity(Map<String, Object> map);

	int insertPayRental(Map<String, Object> map);

	int insertPayRentalItem(RentalPaymentItem rent);

	Integer selectLastInsertId();






}
