package com.camplex.project.camping.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.camplex.project.camping.model.dto.Camp;
import com.camplex.project.camping.model.dto.CampDetail;
import com.camplex.project.camping.model.dto.CampDetailImage;

@Mapper
public interface CampMapper {

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
	 * @param map
	 * @return
	 */
	int insertWishlist(Map<String, Object> map);



}
