package com.camplex.project.camping.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.camplex.project.camping.model.dto.Camp;

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

}
