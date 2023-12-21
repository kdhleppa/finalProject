package com.camplex.project.item.model.service;

import java.util.List;

import com.camplex.project.item.model.dto.FindCartItem;
import com.camplex.project.item.model.dto.Item;
import com.camplex.project.item.model.dto.MembersReservationDate;
import com.camplex.project.paysys.model.dto.rentPayList;

public interface ItemService {

	Item selectDetail(int itemNo);

	List<MembersReservationDate> membersRsvInfo(int memberNo);

	List<Item> selectItemWish(int memberNo);

	List<FindCartItem> membersCartItem(int memberNo);

	/** 아이템 최신순 정렬
	 * @return
	 */
	List<Item> selectItemList();

	/** 아이템 주문 많은 순 정렬
	 * @return
	 */
	List<Item> selectItemListOrder();

	/** 가격 낮은 순 정렬
	 * @return
	 */
	List<Item> selectItemListPriceLow();

	/** 가격 높은 순 정렬
	 * @return
	 */
	List<Item> selectItemListPricehigh();


	List<Item> inCartWishlist(int memberNo);

	/** 아이템 검색
	 * @param input
	 * @return
	 */
	List<Item> searchItem(String input);

	/** 가구
	 * @return
	 */
	List<Item> selectfurnitureItemList();

	/** 텐트
	 * @return
	 */
	List<Item> selectTentItemList();

	/** 가방 악세서리
	 * @return
	 */
	List<Item> selectBagItemList();

	/** 주방
	 * @return
	 */
	List<Item> selectKitchenItemList();

	MembersReservationDate membersRsvInfo2(int reservationNo);

	Item payNow(int itemNo);






	

}
