package com.camplex.project.camping.model.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.camplex.project.camping.model.dto.Camp;
import com.camplex.project.camping.model.dto.CampDetail;
import com.camplex.project.camping.model.dto.CampDetailImage;
import com.camplex.project.member.model.dto.Member;

public interface CampService2 {

	int campInsert(Camp camp, List<MultipartFile> images, MultipartFile inputCampMap)
								throws IllegalStateException, IOException;

	int insertDeCamp(CampDetail campDetail, List<MultipartFile> campDeImges) throws IllegalStateException, IOException;
	

	List<CampDetail> selectDeCamp();

	int deleteCampDe(int campDeNo);

	int deleteCampDeImg(int campDeNo);

	int updateCampDe(int campNo);


	int delCampDeImgNumO();

	/** 캠핑장 삭제
	 * @param campNo
	 * @return
	 */
	int deleteCamp(int campNo);

	/** 캠핑장 no 0 인 캠핑 장소 삭제
	 * @return
	 */
	int delCampNoZ();

	Camp searchCampForCampNo(int campNo);

	List<CampDetail> searchCampDeForCampNo(int campNo);

	int campUpdate(Camp camp, List<MultipartFile> images, MultipartFile inputCampMap) throws IllegalStateException, IOException;

	Member checkCEO(int ceoNum);

	int upInsertDeCamp(CampDetail campDetail, List<MultipartFile> campDeImges) throws IllegalStateException, IOException;

	List<CampDetail> selectDeCampOfCampDeNo(int campNo);

	/** 맵에 사용될 캠핑장 select (ajax)
	 * @return
	 */
	List<Camp> selectForMapOfCamp();

	/** 카테고리에 따른 캠프 select
	 * @param category
	 * @return
	 */
	List<Camp> selectForMapOfCampCategory(String category);








	


	

}
