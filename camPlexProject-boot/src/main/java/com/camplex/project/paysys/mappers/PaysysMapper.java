package com.camplex.project.paysys.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.camplex.project.paysys.model.dto.CartItem;
import com.camplex.project.paysys.model.dto.InfoForReservation;
import com.camplex.project.paysys.model.dto.rentPayList;

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

	/** 캠핑 카카오 결제 내역 저장
	 * @param info
	 * @return
	 */
	int insertKakao(InfoForReservation info);

	/** 캠핑 카카오 결제 내역 결제완료로 변경
	 * @param tempPaymentNo
	 * @return
	 */
	int updatePayState(int tempPaymentNo);

	/** 결제 취소시 내역 삭제(카카오)
	 * @param info
	 * @return
	 */
	int deleteKakao(int tempPaymentNo);

	/** 결제 완료시 예약 테이블 저장(카카오)
	 * @param tempInfo
	 * @return
	 */
	int insertReservation(InfoForReservation tempInfo);



	int deleteAllCart(int memberNo);



	rentPayList selectCheckCart(@Param("cartItemNo") Integer cartItemNo, @Param("memberNo") int memberNo);



	int insertCartNoQuantity(Map<String, Object> map);







}
