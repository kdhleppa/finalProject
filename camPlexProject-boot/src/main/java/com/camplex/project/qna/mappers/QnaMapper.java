package com.camplex.project.qna.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.camplex.project.qna.model.dto.Qna;

@Mapper
public interface QnaMapper {

	public int insertQNA(Qna qna);

	public List<Qna> selectQna();

	public List<Qna> selectQnaY();

	public int insertAnswer(Map<String, Object> map);

	public int updateAnswer(Map<String, Object> map);
	
	
	
}
