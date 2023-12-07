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
	

	/** 캠핑장 삽입
	 *
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int campInsert(Camp camp, List<MultipartFile> images, MultipartFile inputCampMap) 
			throws IllegalStateException, IOException {
		
		String temp = camp.getCampMap();
		String renameCampMap = null;
		
		if(inputCampMap.getSize() > 0) {
			
			renameCampMap = Util.fileRename(inputCampMap.getOriginalFilename());
			
			camp.setCampMap(webPath + renameCampMap);
			
		} else {
			
			camp.setCampMap(null);
			
		}

		int result = mapper.campInsert(camp);
		
		if(renameCampMap != null) {
			inputCampMap.transferTo(new File(filePath + renameCampMap));
		}
		
		if(result == 0) return 0;
		
		int campNo = camp.getCampNo();
		
		if(result > 0) {
			
			List<CampSiteImage> uploadList = new ArrayList<CampSiteImage>();
			
			for(int i=0; i < images.size(); i++) {
				
				if(images.get(i).getSize() > 0) {
					
					CampSiteImage img = new CampSiteImage();
					
					img.setCampImagePath(webPath);
					img.setCampImageOrder(i);
					
					String fileName = images.get(i).getOriginalFilename();
					
					img.setCampImageOriginal(fileName);
					img.setCampNo(campNo);
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

		return campNo;
	}


	/** 캠핑장 구역 삽입
	 *
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insertDeCamp(CampDetail campDetail, List<MultipartFile> campDeImges) 
			throws IllegalStateException, IOException{
		
		int result = mapper.insertDeCamp(campDetail);
		
		if(result == 0) return 0;
		
		int campDeNo = campDetail.getCampDeNo();
		
		if(result > 0) {
			
			List<CampDetailImage> uploadList = new ArrayList<CampDetailImage>();
			
			for(int i = 0; i < campDeImges.size(); i++) {
				
					CampDetailImage img = new CampDetailImage();
					
					img.setCampDeImagePath(webPath);
					img.setCampDeImageOrder(i);
					
					String fileName = campDeImges.get(i).getOriginalFilename();
					
					img.setCampDeImageOriginal(fileName);
					img.setCampDeNo(campDeNo);
					img.setCampDeImageReName(Util.fileRename(fileName));
					
					uploadList.add(img);
	
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
		
		return campDeNo;
	}


	/** 0번 캠핑장 구역 select
	 *
	 */
	@Override
	public List<CampDetail> selectDeCamp() {
		return mapper.selectDeCamp();
	}


	/** 캠핑장 구역 delete
	 *
	 */
	@Override
	public int deleteCampDe(int campDeNo) {
		return mapper.deleteCampDe(campDeNo);
	}


	/** 캠핑장 구역 이미지 delete
	 *
	 */
	@Override
	public int deleteCampDeImg(int campDeNo) {
		return mapper.deleteCampDeImg(campDeNo);
	}


	@Override
	public int updateCampDe(int campNo) {
		return mapper.updateCampDe(campNo);
	}



	@Override
	public int delCampDeImgNumO() {
		return mapper.delCampDeImgNumO();
	}

	/** 캠핑장 삭제
	 *
	 */
	@Override
	public int deleteCamp(int campNo) {
		return mapper.deleteCamp(campNo);
	}


	/** 캠핑장 no 0 인 캠핑 장소 삭제
	 *
	 */
	@Override
	public int delCampNoZ() {
		return mapper.delCampNoZ();
	}




	
	
	
	
}
