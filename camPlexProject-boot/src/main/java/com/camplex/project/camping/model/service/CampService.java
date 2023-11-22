package com.camplex.project.camping.model.service;

import java.util.List;

import com.camplex.project.camping.model.dto.Camp;

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

}
