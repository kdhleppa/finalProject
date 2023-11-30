package com.camplex.project.qna.mappers;

import org.apache.ibatis.annotations.Mapper;

import com.camplex.project.qna.model.dto.ceoQna;

@Mapper
public interface ceoQnaMapper {

	public int insertCeoQna(ceoQna ceoQna);
	
}
