package com.camplex.project.qna.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.camplex.project.qna.mappers.QnaMapper;
import com.camplex.project.qna.model.dto.Qna;

@Service
public class QnaServiceImpl implements QnaService{

	@Autowired
	private QnaMapper mapper;
	
	//  1:1 문의(관리자) 등록
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insertQNA(Qna qna) {
		
		return mapper.insertQNA(qna);
		
	}
	
	//  1:1 문의(관리자) 조회
	@Override
	public List<Qna> selectQna() {
		
		return mapper.selectQna();
	}
	
	//  1:1 문의(관리자) 답변완료조회
	@Override
	public List<Qna> selectQnaY() {

		return mapper.selectQnaY();
	}

	
	//  1:1 문의(관리자) 답변
	@Override
	public int insertAnswer(Map<String, Object> map) {

		return mapper.insertAnswer(map);
	}

	//  1:1 문의(관리자) 답변수정
	@Override
	public int updateAnswer(Map<String, Object> map) {

		return mapper.updateAnswer(map);
	}

	

}
