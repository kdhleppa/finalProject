package com.camplex.project.qna.model.service;

import java.util.List;
import java.util.Map;

import com.camplex.project.qna.model.dto.Qna;
import com.camplex.project.qna.model.dto.ceoQna;

public interface ceoQnaService {

	/** 캠핑장 문의사항 등록
	 * @param ceoQna
	 * @return
	 */
	int insertCeoQna(ceoQna ceoQna);

	// 1:1 문의(관리자) 리스트 이동
	List<ceoQna> selectCeoQna();

	List<ceoQna> selectCeoQnaY();

	int insertCeoAnswer(Map<String, Object> map);

	int updateCeoAnswer(Map<String, Object> map);
	
	

}
