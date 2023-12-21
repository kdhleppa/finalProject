package com.camplex.project.camping.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.camplex.project.camping.model.dto.Camp;
import com.camplex.project.camping.model.dto.CampCeoImage;
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

	/** ceo사진 업로드
	 * @param uploadList
	 * @return
	 */
	int insertImages(List<CampCeoImage> uploadList);

	/** ceo사진 지우기
	 * @param imgNo
	 * @return
	 */
	int ceoPicDelete(int imgNo);

	/** 예약 안된 캠핑장 호실 조회
	 * @param map
	 * @return
	 */
	List<CampDetail> selectCampDetailListNotRes(Map<String, Object> map);

	/** 별점 개수 조회
	 * @param map
	 * @return
	 */
	int selectStar(Map<String, Object> map);

	/** 별점 눌렀는지 확인
	 * @param map 
	 * @return
	 */
	int checkStar(Map<String, Object> map);

	/** 별점 업데이트
	 * @param map
	 * @return
	 */
	int updateStar(Map<String, Object> map);

	/** 별점 추가
	 * @param map
	 * @return
	 */
	int insertStar(Map<String, Object> map);

	/** 캠핑장 정렬 추천순(별점 높은 순)
	 * @return
	 */
	List<Camp> selectCampListReccomend();

	/** 캠핑장 정렬 인기순(별점 카운트 높은 순)
	 * @return
	 */
	List<Camp> selectCampListPopular();

	/** 캠핑장 정렬 오래된순
	 * @return
	 */
	List<Camp> selectCampListOld();

	/** 캠핑장 검색
	 * @param input
	 * @return
	 */
	List<Camp> searchCampList(String input);

	/** 카테고리 분류
	 * @param category
	 * @return
	 */
	List<Camp> selectCampListCategory(String category);



}
