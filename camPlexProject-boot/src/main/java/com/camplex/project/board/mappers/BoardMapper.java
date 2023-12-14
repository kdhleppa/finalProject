package com.camplex.project.board.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.camplex.project.board.model.dto.Board;

@Mapper
public interface BoardMapper {

	/** 게시판 종류 조회
	 * @return boardTypeList
	 */
	List<Map<String, Object>> selectBoardTypeList();


	/** 특정 게시판의 삭제되지 않은 게시글 수 조회
	 * @param boardType
	 * @return listCount
	 */
	public int getListCount(String boardType);


	/** 특정 게시판에서 현재 페이지에 해당하는 부분에 대한 게시글 목록 조회
	 * @param pagination
	 * @param boardType
	 * @return
	 */
	public List<Board> selectBoardList(String boardType, RowBounds rowBounds);


	/** 게시글 상세 조회
	 * @param map
	 * @return board
	 */
	public Board selectBoard(Map<String, Object> map);


	/** 좋아요 여부 확인 DAO
	 * @param map
	 * @return result
	 */
	public int boardLikeCheck(Map<String, Object> map);


	/** 조회수 증가 DAO
	 * @param boardNo
	 * @return result
	 */
	public int updateReadCount(int boardNo);

	
	/** 좋아요 테이블 삽입
	 * @param paramMap
	 * @return
	 */
	public int insertBoardLike(Map<String, Integer> paramMap);

	/** 좋아요 삭제
	 * @param paramMap
	 * @return
	 */
	public int deleteBoardLike(Map<String, Integer> paramMap);

	/** 좋아요 개수 조회
	 * @param integer
	 * @return
	 */
	public int countBoardLike(Integer boardNo);

	/** 게시글 수 조회 (검색)
	 * @param paramMap
	 * @return
	 */
	public int getSearchListCount(Map<String, Object> paramMap);

	/** 게시글 목록 조회 (검색)
	 * @param pagination
	 * @param paramMap
	 * @return
	 */
	public List<Board> selectSearchBoardList(Map<String, Object> paramMap, RowBounds rowBounds);

	/** DB 이미지 파일 목록 조회
	 * @return
	 */
	public List<String> selectImageList();

	// 조회수 순 정렬
	List<Board> orderReadCount(String boardType);

	// 좋아요 순 정렬
	List<Board> orderLikeCount(String boardType);
	
	
	
}
