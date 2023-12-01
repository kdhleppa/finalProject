package com.camplex.project.qna.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.camplex.project.qna.mappers.ceoQnaMapper;
import com.camplex.project.qna.model.dto.ceoQna;

@Service
public class ceoQnaServiceImpl implements ceoQnaService{

	@Autowired
	private ceoQnaMapper mapper;
	
	/** 캠핑장 1:1 문의 입력
	 *
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insertCeoQna(ceoQna ceoQna) {
		
		return mapper.insertCeoQna(ceoQna);
		
	}

}
