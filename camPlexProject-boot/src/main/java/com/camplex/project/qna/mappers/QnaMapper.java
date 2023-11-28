package com.camplex.project.qna.mappers;

import org.apache.ibatis.annotations.Mapper;

import com.camplex.project.qna.model.dto.Qna;

@Mapper
public interface QnaMapper {

	int insertQNA(Qna qna);

	
}
