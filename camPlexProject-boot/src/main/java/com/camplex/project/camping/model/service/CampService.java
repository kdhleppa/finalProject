package com.camplex.project.camping.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.camplex.project.camping.model.dto.Camp;
import com.camplex.project.camping.model.dto.CampDetail;
import com.camplex.project.camping.model.dto.CampDetailImage;

public interface CampService {

	/** 캠프장 목록 조회
	 * @return
	 */
	List<Camp> selectCampList();

	/** 캠프장 상세 조회
	 * @param campNo
	 * @return
	 */
	Camp selectCampOne(int campNo);

	/** 캠핑장 예약 이동
	 * @param campDeNo
	 * @return
	 */
	CampDetail selectCampDetail(int campDeNo);

	/** 캠핑장 예약 이동 시 이미지 불러오기
	 * @param campDeNo
	 * @return
	 */
	List<CampDetailImage> selectCampDetailImageList(int campDeNo);

	/** 위시리스트 중복 검사
	 * @param map
	 * @return
	 */
	int checkDupCampWish(Map<String, Object> map);
	
	/** 위시리스트에 캠핑장 추가
	 * @param campNo
	 * @param memberNo
	 * @return
	 */
	int insertWishlist(Map<String, Object> map);

	/** ceo사진 업로드
	 * @param campNo
	 * @param images
	 * @return
	 * @throws Exception 
	 */
	int insertCeoPic(int campNo, List<MultipartFile> images) throws Exception;

	

}
