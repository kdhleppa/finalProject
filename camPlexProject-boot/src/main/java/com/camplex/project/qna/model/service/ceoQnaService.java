package com.camplex.project.qna.model.service;

import com.camplex.project.qna.model.dto.ceoQna;

public interface ceoQnaService {

	/** 캠핑장 문의사항 등록
	 * @param ceoQna
	 * @return
	 */
	int insertCeoQna(ceoQna ceoQna);

}
