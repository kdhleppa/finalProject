package com.camplex.project.qna.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.camplex.project.qna.mappers.QnaMapper;
import com.camplex.project.qna.model.dto.Qna;

@Service
public class QnaServiceImpl implements QnaService{

	@Autowired
	private QnaMapper mapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insertQNA(Qna qna) {
		
		return mapper.insertQNA(qna);
		
	}

}
