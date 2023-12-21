package com.camplex.project.qna.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.camplex.project.qna.model.dto.Qna;
import com.camplex.project.qna.model.dto.ceoQna;

@Mapper
public interface ceoQnaMapper {

	/** 캠핑장 1:1 문의 입력
	 * @param ceoQna
	 * @return
	 */
	public int insertCeoQna(ceoQna ceoQna);
	
	public List<ceoQna> selectCeoQna();

	public List<ceoQna> selectCeoQnaY();

	public int insertCeoAnswer(Map<String, Object> map);

	public int updateCeoAnswer(Map<String, Object> map);
	
}
