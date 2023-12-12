package com.camplex.project.item.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.camplex.project.item.model.dto.Item;
import com.camplex.project.item.model.dto.ItemImg;

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

	/** 아이템 검색
	 * @param input
	 * @return
	 */
	List<Item> searchItem(String input);

	/** 아이템 카테고리 분류
	 * @param category
	 * @return
	 */
	List<Item> selectItemCategory(String category);

	/** 가구
	 * @return
	 */
	List<Item> selectfurnitureItemList();

	/** 텐트
	 * @return
	 */
	List<Item> selectTentItemList();

	/** 가방/악세서리
	 * @return
	 */
	List<Item> selectBagItemList();

	/** 주방
	 * @return
	 */
	List<Item> selectKitchenItemList();

	/** 상품 삭제(업뎃)
	 * @param itemNo
	 * @return
	 */
	int deleteItem(int itemNo);

	/** 상품 업로드
	 * @param item
	 * @return
	 */
	int itemInsert(Item item);

	/** 상품 이미지 업로드
	 * @param uploadList
	 * @return
	 */
	int insertItemImageList(List<ItemImg> uploadList);
	

}
