package com.camplex.project.qna.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.camplex.project.qna.mappers.ceoQnaMapper;
import com.camplex.project.qna.model.dto.Qna;
import com.camplex.project.qna.model.dto.ceoQna;

@Service
public class ceoQnaServiceImpl implements ceoQnaService{

	@Autowired
	private ceoQnaMapper mapper;
	
	
	//  1:1 문의(관리자) 조회
	@Override
	public List<ceoQna> selectCeoQna() {
		
		return mapper.selectCeoQna();
	}
	
	//  1:1 문의(관리자) 답변완료조회
	@Override
	public List<ceoQna> selectCeoQnaY() {

		return mapper.selectCeoQnaY();
	}

	
	//  1:1 문의(관리자) 답변
	@Override
	public int insertCeoAnswer(Map<String, Object> map) {

		return mapper.insertCeoAnswer(map);
	}

	//  1:1 문의(관리자) 답변수정
	@Override
	public int updateCeoAnswer(Map<String, Object> map) {

		return mapper.updateCeoAnswer(map);
	}
	
	/** 캠핑장 1:1 문의 입력
	 *
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insertCeoQna(ceoQna ceoQna) {
		
		return mapper.insertCeoQna(ceoQna);
		
	}

}
