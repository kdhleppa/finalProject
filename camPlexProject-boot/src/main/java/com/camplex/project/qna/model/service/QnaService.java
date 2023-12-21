package com.camplex.project.qna.model.service;

import java.util.List;
import java.util.Map;

import com.camplex.project.qna.model.dto.Qna;

public interface QnaService {

	int insertQNA(Qna qna);

	List<Qna> selectQna();

	List<Qna> selectQnaY();

	int insertAnswer(Map<String, Object> map);

	int updateAnswer(Map<String, Object> map);

}
