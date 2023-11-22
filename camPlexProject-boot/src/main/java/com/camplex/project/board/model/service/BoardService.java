package com.camplex.project.board.model.service;

import java.util.List;
import java.util.Map;

import com.camplex.project.board.model.dto.Board;

public interface BoardService {
	
	/** 게시판 종류 조회
	 * @return boardTypeList
	 */
	List<Map<String, Object>> selectBoardTypeList();

	/** 게시글 목록 조회
	 * @param boardType
	 * @param cp
	 * @return map
	 */
	Map<String, Object> selectBoardList(String boardType, int cp);

	
	/** 게시글 목록 조회 (검색)
	 * @param paramMap
	 * @param cp
	 * @return boardList
	 */
	Map<String, Object> selectBoardList(Map<String, Object> paramMap, int cp);

//	/** 게시글 상세 조회
//	 * @param map
//	 * @return board
//	 */
//	Board selectBoard(Map<String, Object> map);
//
//	/** 좋아요 여부 확인 서비스
//	 * @param map
//	 * @return result
//	 */
//	int boardLikeCheck(Map<String, Object> map);
//
//	/** 조회수 증가 서비스
//	 * @param boardNo
//	 * @return
//	 */
//	int updateReadCount(int boardNo);
//
//	int like(Map<String, Integer> paramMap);
//
//	/** DB 이미지(파일) 목록 조회
//	 * @return
//	 */
//	List<String> selectImageList();

	
}
