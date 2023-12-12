package com.camplex.project.item.model.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.camplex.project.board.model.exception.FileUploadException;
import com.camplex.project.common.utility.Util;
import com.camplex.project.item.mappers.ItemMapper2;
import com.camplex.project.item.model.dto.Item;
import com.camplex.project.item.model.dto.ItemImg;

@Service
public class ItemServiceImpl2 implements ItemService2{
	
	@Autowired
	private ItemMapper2 mapper;
	
	@Value("${camplex.item.webpath}")
	private String webPath;
	
	@Value("${camplex.item.location}")
	private String filePath;

	@Override
	public List<Item> searchItems() {
		
		return mapper.searchItems();
	}

	@Override
	public int deleteItem(int itemNo) {
		return mapper.deleteItem(itemNo);
	}

	/** 상품 업로드
	 *
	 */
	@Override
	public int uploadItem(Item item, List<MultipartFile> itemImages) 
			throws IllegalStateException, IOException{
		
		int result = mapper.itemInsert(item);
		
		if(result == 0) return 0;
		
		int itemNo = item.getItemNo();
		
		if(itemNo > 0) {
			
			List<ItemImg> uploadList = new ArrayList<ItemImg>();
			
			for(int i = 0; i < itemImages.size(); i++) {
				
				if(itemImages.get(i).getSize() > 0) {
					
					ItemImg img = new ItemImg();
					
					img.setItemImgPath(webPath);
					img.setItemNo(itemNo);
					img.setItemImgOrder(i);
					
					String fileName = itemImages.get(i).getOriginalFilename();
					
					img.setItemImgOriginal(fileName);
					img.setItemImgRename( Util.fileRename(fileName) );
					
					uploadList.add(img);
				}
				
			}
			
			if(!uploadList.isEmpty()) {
				
				result = mapper.insertItemImageList(uploadList);
				
				if(result == uploadList.size()) {
					
					for(int i = 0; i < uploadList.size(); i++) {
						
						int index = uploadList.get(i).getItemImgOrder();
						
						String rename = uploadList.get(i).getItemImgRename();
						
						itemImages.get(index).transferTo(new File(filePath + rename));
						
					}
					
				} else {
					throw new FileUploadException();
				}
				
			}
			
			
		}
		
		return result;
	}
}
