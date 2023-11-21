package com.camplex.project.camping.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camplex.project.camping.mappers.CampMapper;
import com.camplex.project.camping.model.dto.Camp;

@Service
public class campServiceImpl implements campService {

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

}
