package com.camplex.project.item.model.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.camplex.project.board.model.exception.FileUploadException;
import com.camplex.project.common.utility.Util;
import com.camplex.project.item.mappers.ItemMapper2;
import com.camplex.project.item.model.dto.Item;
import com.camplex.project.item.model.dto.ItemImg;

@Service
@PropertySource("classpath:/config.properties")
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
	@Transactional(rollbackFor = Exception.class)
	public int deleteItem(int itemNo) {
		return mapper.deleteItem(itemNo);
	}

	/** 상품 업로드
	 *
	 */
	@Transactional(rollbackFor = Exception.class)
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

	/** 상품 넘버에 따른 상품 조회
	 *
	 */
	@Override
	public Item selectItemOfItemNo(int itemNo) {
		return mapper.selectItemOfItemNo(itemNo);
	}

	/** 상품 수정
	 *
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int editItem(Item item, List<MultipartFile> images) throws IllegalStateException, IOException  {
		
		int rowCount = mapper.itemUpdate(item);
		
		
		// 2. 게시글 부분이 수정 성공 했을 때
		if(rowCount > 0) {
			
			// 4. 새로 업로드된 이미지 분류 작업
			// images : 실제 파일이 담긴 List
			//         -> input type="file" 개수만큼 요소가 존재
			//         -> 제출된 파일이 없어도 MultipartFile 객체가 존재
			
			List<ItemImg> uploadList = new ArrayList<>();
			
			for(int i=0 ; i<images.size(); i++) {
				
				if(images.get(i).getSize() > 0) { // 업로드된 파일이 있을 경우
					
					// BoardImage 객체를 만들어 값 세팅 후 
					// uploadList에 추가
					ItemImg img = new ItemImg();
					
					// img에 파일 정보를 담아서 uploadList에 추가
					img.setItemImgPath(webPath); // 웹 접근 경로
					img.setItemNo(item.getItemNo()); // 게시글 번호
					img.setItemImgOrder(i); // 이미지 순서
					
					// 파일 원본명
					String fileName = images.get(i).getOriginalFilename();
					
					img.setItemImgOriginal(fileName); // 원본명
					img.setItemImgRename( Util.fileRename(fileName) ); // 변경명    
					
					
					// 오라클은 다중 UPDATE를 지원하지 않기 때문에
					// 하나씩 UPDATE 수행
					uploadList.add(img);
					
					rowCount = mapper.itemImageUpdate(img);
					
					if(rowCount == 0) {
						// 수정 실패 == DB에 이미지가 없었다 
						// -> 이미지를 삽입
						rowCount = mapper.itemImageInsert(img);
					}
				}
			}
			
			
			// 5. uploadList에 있는 이미지들만 서버에 저장(transferTo())
			if(!uploadList.isEmpty()) {
				for(int i=0 ; i< uploadList.size(); i++) {
					
					int index = uploadList.get(i).getItemImgOrder();
					
					// 파일로 변환
					String rename = uploadList.get(i).getItemImgRename();
					
					images.get(index).transferTo( new File(filePath + rename)  );                    
				}
			}

		}
		
		
		return rowCount;
		
	}
	
	
}
