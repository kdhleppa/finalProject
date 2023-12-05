package com.camplex.project.item.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.camplex.project.item.model.dto.Item;

@Mapper
public interface ItemMapper2 {

	List<Item> searchItems();
	
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
	

}
