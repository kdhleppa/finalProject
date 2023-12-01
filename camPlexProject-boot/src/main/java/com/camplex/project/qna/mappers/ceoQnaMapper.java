package com.camplex.project.qna.mappers;

import org.apache.ibatis.annotations.Mapper;

import com.camplex.project.qna.model.dto.ceoQna;

@Mapper
public interface ceoQnaMapper {

	/** 캠핑장 1:1 문의 입력
	 * @param ceoQna
	 * @return
	 */
	public int insertCeoQna(ceoQna ceoQna);
	
}
