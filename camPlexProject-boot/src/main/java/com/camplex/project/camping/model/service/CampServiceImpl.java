package com.camplex.project.camping.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camplex.project.camping.mappers.CampMapper;
import com.camplex.project.camping.model.dto.Camp;
import com.camplex.project.camping.model.dto.CampDetail;
import com.camplex.project.camping.model.dto.CampDetailImage;

@Service
public class CampServiceImpl implements CampService {

	@Autowired
	private CampMapper mapper;
	
	/** 캠프장 목록 조회
	 *
	 */
	@Override
	public List<Camp> selectCampList() {
		
		return mapper.selectCampList();
	}

	/** 캠프장 상세 조회
	 *
	 */
	@Override
	public Camp selectCampOne(int campNo) {
		
		return mapper.selectCampOne(campNo);
	}

	/** 캠핑장 예약 이동
	 *
	 */
	@Override
	public CampDetail selectCampDetail(int campDeNo) {
		
		return mapper.selectCampDetail(campDeNo);
	}

	/** 캠핑장 예약 이동 시 이미지 불러오기
	 *
	 */
	@Override
	public List<CampDetailImage> selectCampDetailImageList(int campDeNo) {
		
		return mapper.selectCampDetailImageList(campDeNo);
	}

	/** 위시리스트 중복 검사
	 *
	 */
	@Override
	public int checkDupCampWish(Map<String, Object> map) {

		return mapper.checkDupCampWish(map);
	}
	
	/** 위시리스트에 캠핑장 추가
	 *
	 */
	@Override
	public int insertWishlist(Map<String, Object> map) {

		return mapper.insertWishlist(map);
	}

	

}
