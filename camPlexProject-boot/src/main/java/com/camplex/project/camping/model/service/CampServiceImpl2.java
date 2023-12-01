package com.camplex.project.camping.model.service;

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
import com.camplex.project.camping.mappers.CampMapper2;
import com.camplex.project.camping.model.dto.Camp;
import com.camplex.project.camping.model.dto.CampDetail;
import com.camplex.project.camping.model.dto.CampDetailImage;
import com.camplex.project.camping.model.dto.CampSiteImage;
import com.camplex.project.common.utility.Util;



@Service
@PropertySource("classpath:/config.properties")
public class CampServiceImpl2 implements CampService2{

	@Autowired
	private CampMapper2 mapper;
	
	@Value("${camplex.camping.webpath}")
	private String webPath;
	
	@Value("${camplex.camping.location}")
	private String filePath;
	

	/** 캠핑 장소 삽입
	 *
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int campInsert(Camp camp, List<MultipartFile> images) 
			throws IllegalStateException, IOException {
		
		int result = mapper.campInsert(camp);
		
		if(result == 0) return 0;
		
		if(result > 0) {
			
			List<CampSiteImage> uploadList = new ArrayList<CampSiteImage>();
			
			for(int i=0; i < images.size(); i++) {
				
				if(images.get(i).getSize() > 0) {
					
					CampSiteImage img = new CampSiteImage();
					
					img.setCampImagePath(webPath);
					img.setCampImageOrder(i);
					
					String fileName = images.get(i).getOriginalFilename();
					
					img.setCampImageOriginal(fileName);
					img.setCampImageReName(Util.fileRename(fileName));
					
					uploadList.add(img);
					
				}
				
			}
			
			if(!uploadList.isEmpty()) {
				
				result = mapper.insertImageList(uploadList);
				
				if(result == uploadList.size()) {
					
					for(int i=0; i < uploadList.size(); i++) {
						
						int index = uploadList.get(i).getCampImageOrder();
						
						String rename = uploadList.get(i).getCampImageReName();
						
						images.get(index).transferTo(new File(filePath + rename));
								
					}
					
					
				} else {
					
					throw new FileUploadException();
					
				}
				
			}
			
			
		}

		return result;
	}


	/** 캠핑장 구역 insert
	 *
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insertDeCamp(CampDetail campDetail, List<MultipartFile> campDeImges) 
			throws IllegalStateException, IOException{
		
		int result = mapper.insertDeCamp(campDetail);
		
		if(result == 0) return 0;
		
		int campDeNo = campDetail.getCampDeNo();
		
		if(campDeNo > 0) {
			
			List<CampDetailImage> uploadList = new ArrayList<CampDetailImage>();
			
			for(int i = 0; i < campDeImges.size(); i++) {
				
				if(campDeImges.get(i).getSize() > 0) {
					
					CampDetailImage img = new CampDetailImage();
					
					img.setCampDeImagePath(webPath);
					img.setCampDeNo(campDeNo);
					img.setCampDeImageOrder(i);
					
					String fileName = campDeImges.get(i).getOriginalFilename();
					
					img.setCampDeImageOriginal(fileName);
					img.setCampDeImageReName(Util.fileRename(fileName));
					
					uploadList.add(img);
					
				}
				
			}
			
			if(!uploadList.isEmpty()) {
				
				result = mapper.insertCampDetailImageList(uploadList);
				
				if(result == uploadList.size()) {
					
					for(int i = 0; i < uploadList.size(); i++) {
						
						int index = uploadList.get(i).getCampDeImageOrder();
						
						String rename = uploadList.get(i).getCampDeImageReName();
						
						
						campDeImges.get(index).transferTo(new File(filePath + rename));
						
					}
					
				} else {
					
					throw new FileUploadException();
					
				}
				
			}
			
		}
		
		return result;
	}


	/** 0번 캠핑장 구역 select
	 *
	 */
	@Override
	public List<CampDetail> selectDeCamp() {
		return mapper.selectDeCamp();
	}

	
	
	
	
}
