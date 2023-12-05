package com.camplex.project.item.model.service;

import java.util.List;

import com.camplex.project.item.model.dto.FindCartItem;
import com.camplex.project.item.model.dto.Item;
import com.camplex.project.item.model.dto.MembersReservationDate;

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

	/** 아이템 검색
	 * @param input
	 * @return
	 */
	List<Item> searchItem(String input);




	

}
