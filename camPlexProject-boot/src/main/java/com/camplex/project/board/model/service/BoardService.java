package com.camplex.project.board.model.service;

import java.util.Map;

import com.camplex.project.board.model.dto.Board;

public interface BoardService {

	/** 게시글 목록 조회
	 * @param boardCode
	 * @param cp
	 * @return map
	 */
	Map<String, Object> selectBoardList(int boardCode, int cp);
	
	/** 게시글 목록 조회 (검색)
	 * @param paramMap
	 * @param cp
	 * @return boardList
	 */
	Map<String, Object> selectBoardList(Map<String, Object> paramMap, int cp);

	/** 게시글 상세 조회
	 * @param map
	 * @return board
	 */
	Board selectBoard(Map<String, Object> map);

	/** 조회수 증가 서비스
	 * @param boardNo
	 * @return
	 */
	int updateReadCount(int boardNo);

	int like(Map<String, Integer> paramMap);

	
	
}
