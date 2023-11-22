package com.camplex.project.camping.model.service;

import java.util.List;

import com.camplex.project.camping.model.dto.Camp;
import com.camplex.project.camping.model.dto.CampDetail;

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

}
