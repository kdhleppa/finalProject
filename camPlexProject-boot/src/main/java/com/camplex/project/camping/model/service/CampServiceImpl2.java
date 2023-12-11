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
import com.camplex.project.member.model.dto.Member;



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
		
		System.out.println("campNo ::" + campNo);
		
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


	/** 캠프 no에 따른 조회
	 *
	 */
	@Override
	public Camp searchCampForCampNo(int campNo) {
		return mapper.searchCampForCampNo(campNo);
	}


	@Override
	public List<CampDetail> searchCampDeForCampNo(int campNo) {
		
		int countDe = mapper.countDe(campNo);
		
		System.out.println("countDe :: " + countDe);
		
		List<CampDetail> campDe = new ArrayList<CampDetail>();
		
		int campDeNo = 0;
		
		if(countDe == 0) {
			return campDe;
		}
		
		if(countDe > 0) {
			
			campDe = mapper.searchCampDeForCampNo(campNo);
			
			for(int i = 0; i < campDe.size(); i++) {
				
					campDeNo = campDe.get(i).getCampDeNo();
					
					
					List<CampDetailImage> camDImg = mapper.selectCampDetailImageList(campDeNo);
					
					
					campDe.get(i).setCampDetailImageList(camDImg);
					
			}


			
		}
		
		
		
		return campDe;
	}


	/** 캠핑장 업데이트
	 *
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int campUpdate(Camp camp, List<MultipartFile> images, MultipartFile inputCampMap)throws IllegalStateException, IOException {

		String renameCampMap = null;
		
		if(inputCampMap.getSize() > 0) {
				
				renameCampMap = Util.fileRename(inputCampMap.getOriginalFilename());
				
				camp.setCampMap(webPath + renameCampMap);
				
			} else {
				
				camp.setCampMap(null);
				
			}
		
		
		int rowCount = mapper.campUpdate(camp);
		
		if(renameCampMap != null) {
			inputCampMap.transferTo(new File(filePath + renameCampMap));
		}
			
			
			// 4. 새로 업로드된 이미지 분류 작업
			// images : 실제 파일이 담긴 List
			//         -> input type="file" 개수만큼 요소가 존재
			//         -> 제출된 파일이 없어도 MultipartFile 객체가 존재
			
			List<CampSiteImage> uploadList = new ArrayList<CampSiteImage>();
			
			for(int i=0 ; i<images.size(); i++) {
				
				if(images.get(i).getSize() > 0) { // 업로드된 파일이 있을 경우
					
					CampSiteImage img = new CampSiteImage();
					
					// img에 파일 정보를 담아서 uploadList에 추가
					img.setCampImagePath(webPath); // 웹 접근 경로
					img.setCampNo(camp.getCampNo()); // 캠프장 번호
					img.setCampImageOrder(i); // 이미지 순서
					
					// 파일 원본명
					String fileName = images.get(i).getOriginalFilename();
					
					img.setCampImageOriginal(fileName); // 원본명
					img.setCampImageReName( Util.fileRename(fileName) ); // 변경명    
					
					
					// 오라클은 다중 UPDATE를 지원하지 않기 때문에
					// 하나씩 UPDATE 수행
					uploadList.add(img);
					
					rowCount = mapper.campImageUpdate(img);
					
					if(rowCount == 0) {
						// 수정 실패 == DB에 이미지가 없었다 
						// -> 이미지를 삽입
						rowCount = mapper.campImageInsert(img);
					}
				}
			}
			
			
			// 5. uploadList에 있는 이미지들만 서버에 저장(transferTo())
			if(!uploadList.isEmpty()) {
				for(int i=0 ; i< uploadList.size(); i++) {
					
					int index = uploadList.get(i).getCampImageOrder();
					
					// 파일로 변환
					String rename = uploadList.get(i).getCampImageReName();
					
					images.get(index).transferTo( new File(filePath + rename)  );                    
				}
			}


		
		return rowCount;
		
	}


	/** 멤버 넘버가 CEO 인지 검사
	 *
	 */
	@Override
	public Member checkCEO(int ceoNum) {
		return mapper.checkCEO(ceoNum);
	}


	@Override
	public int updateCampDeToZ(CampDetail campDetail) {
		
		return mapper.updateCampDeToZ(campDetail);
	}


	




	
	
	
	
}
