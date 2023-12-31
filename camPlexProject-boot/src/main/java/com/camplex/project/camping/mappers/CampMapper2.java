package com.camplex.project.camping.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import com.camplex.project.camping.model.dto.Camp;
import com.camplex.project.camping.model.dto.CampDetail;
import com.camplex.project.camping.model.dto.CampDetailImage;
import com.camplex.project.camping.model.dto.CampSiteImage;
import com.camplex.project.member.model.dto.Member;

@Mapper
public interface CampMapper2 {

	/** 캠프장 삽입(캠프장 이름, 주소, 전화번호, 부대 시설(옵션), 주변 환경, 매너 타임 등)
	 * @param camp
	 * @param images
	 * @return
	 */
	int campInsert(Camp camp);

	/** 캠프장 삽입(이미지)
	 * @param uploadList
	 * @return
	 */
	int insertImageList(List<CampSiteImage> uploadList);


	/** 0번 캠핑장 구역 select
	 * @return
	 */
	List<CampDetail> selectDeCamp();

	/** 캠핑장 구역 (이미지X) 삽입
	 * @param campDetail
	 * @return
	 */
	int insertDeCamp(CampDetail campDetail);

	/** 캠핑장 구역 이미지 삽입
	 * @param uploadList
	 * @return
	 */
	int insertCampDetailImageList(List<CampDetailImage> uploadList);

	/** 캠핑장 구역 delete
	 * @param campDeNo
	 * @return
	 */
	int deleteCampDe(int campDeNo);

	/** 캠핑장 구역 이미지 delete
	 * @param campDeNo
	 * @return
	 */
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

	List<CampDetailImage> selectCampDetailImageList(int campDeNo);

	int countDe(int campNo);

	int campUpdate(Camp camp);

	int campImageUpdate(CampSiteImage img);

	int campImageInsert(CampSiteImage img);

	Member checkCEO(int ceoNum);

	int upInsertDeCamp(CampDetail campDetail);

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
