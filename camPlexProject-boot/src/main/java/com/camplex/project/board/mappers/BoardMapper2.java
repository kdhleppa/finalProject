//package com.camplex.project.board.mappers;
//
//import java.util.List;
//import java.util.Map;
//
//import org.apache.ibatis.annotations.Mapper;
//
//import com.camplex.project.board.model.dto.Board;
//import com.camplex.project.board.model.dto.BoardImage;
//
//@Mapper
//public interface BoardMapper2 {
//	
//	/** 게시글 삽입
//	 * @param board
//	 * @return boardNo
//	 */
//	public int boardInsert(Board board);
//
//	/** 이미지 리스트 삽입
//	 * @param uploadList 
//	 * @return result
//	 */
//	public int insertImageList(List<BoardImage> uploadList);
//
//	/** 게시글 수정 
//	 * @param board
//	 * @return rowCount
//	 */
//	public int boardUpdate(Board board);
//
//	/** 이미지 삭제
//	 * @param deleteMap
//	 * @return rowCount
//	 */
//	public int imageDelete(Map<String, Object> deleteMap);
//
//	/** 이미지 수정
//	 * @param img
//	 * @return
//	 */
//	public int imageUpdate(BoardImage img);
//
//	/** 이미지 삽입
//	 * @param img
//	 * @return rowCount
//	 */
//	public int imageInsert(BoardImage img);
//	
//
//	/** 게시글 삭제
//	 * @param map
//	 * @return result
//	 */
//	public int boardDelete(Map<String, Object> map);
//	
//	
//}
