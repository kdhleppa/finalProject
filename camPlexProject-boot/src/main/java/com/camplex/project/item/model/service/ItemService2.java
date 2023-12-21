package com.camplex.project.item.model.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.camplex.project.item.model.dto.Item;

public interface ItemService2 {

	/** 상품 전체 조회
	 * @return
	 */
	List<Item> searchItems();

	/** 상품 삭제
	 * @param itemNo
	 * @return
	 */
	int deleteItem(int itemNo);

	/** 상품 삽입
	 * @param item
	 * @param itemImages
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	int uploadItem(Item item, List<MultipartFile> itemImages) 
			throws IllegalStateException, IOException;

	/** 상품 넘버에 따른 상품 조회
	 * @param itemNo
	 * @return
	 */
	Item selectItemOfItemNo(int itemNo);

	/** 상품 정보 수정
	 * @param item
	 * @param images
	 * @return
	 */
	int editItem(Item item, List<MultipartFile> images) throws IllegalStateException, IOException;
	
}
