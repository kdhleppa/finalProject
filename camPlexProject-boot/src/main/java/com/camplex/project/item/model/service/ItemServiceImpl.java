package com.camplex.project.item.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.camplex.project.item.mappers.ItemMapper;
import com.camplex.project.item.mappers.ItemMapper2;
import com.camplex.project.item.model.dto.FindCartItem;
import com.camplex.project.item.model.dto.Item;
import com.camplex.project.item.model.dto.MembersReservationDate;

@Service
@PropertySource("classpath:/config.properties")
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper mapper;
	
	@Autowired
	private ItemMapper2 mapper2;
	
	/** 상세보기 조회
	 *
	 */
	@Override
	public Item selectDetail(int itemNo) {
		
		return mapper.selectDetail(itemNo);
	}

	/** 상품상세보기로그인시예약해둔캠핑장정보조회
	 *
	 */
	@Override
	public List<MembersReservationDate> membersRsvInfo(int memberNo) {
		
		return mapper.membersRsvInfo(memberNo);
	}

	@Override
	public List<Item> selectItemWish(int memberNo) {
		return mapper.selectItemWish(memberNo);
	}

	@Override
	public List<FindCartItem> membersCartItem(int memberNo) {
		return mapper.membersCartItem(memberNo);
	}

	
//	목록 조회가 mapper2로 설정되어 있어서 정렬도 mapper2로 갑니다 
	
	/** 아이템 최신순 정렬
	 *
	 */
	@Override
	public List<Item> selectItemList() {
		return mapper2.selectItemList();
	}

	/** 아이템 주문 많은 순 정렬
	 *
	 */
	@Override
	public List<Item> selectItemListOrder() {
		return mapper2.selectItemListOrder();
	}

	
	/** 가격 낮은 순 정렬
	 *
	 */
	@Override
	public List<Item> selectItemListPriceLow() {
		return mapper2.selectItemListPriceLow();
	}
	

	/** 가격 높은 순 정렬
	 * 
	 */
	@Override
	public List<Item> selectItemListPricehigh() {
		return mapper2.selectItemListPricehigh();
	}


	@Override
	public List<Item> inCartWishlist(int memberNo) {
		return mapper.inCartWishlist(memberNo);
	}
	/** 아이템 검색
	 *
	 */
	@Override
	public List<Item> searchItem(String input) {
		return mapper2.searchItem(input);
	}

	/** 가구
	 *
	 */
	@Override
	public List<Item> selectfurnitureItemList() {
		return mapper2.selectfurnitureItemList();
	}

	/** 텐트
	 *
	 */
	@Override
	public List<Item> selectTentItemList() {
		return mapper2.selectTentItemList();
	}

	/** 가방
	 *
	 */
	@Override
	public List<Item> selectBagItemList() {
		return mapper2.selectBagItemList();
	}

	/** 주방
	 *
	 */
	@Override
	public List<Item> selectKitchenItemList() {
		return mapper2.selectKitchenItemList();

	}

	
	
	
	
	

}
