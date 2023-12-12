package com.camplex.project.item.model.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.camplex.project.item.model.dto.Item;

public interface ItemService2 {

	List<Item> searchItems();

	int deleteItem(int itemNo);

	int uploadItem(Item item, List<MultipartFile> itemImages) 
			throws IllegalStateException, IOException;
	
}
