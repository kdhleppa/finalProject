package com.camplex.project.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.camplex.project.board.model.dto.Comment;
import com.camplex.project.board.model.service.CommentService;

// @Controller + @ResponseBody

@RestController // 요청/응답 처리(단, 모든 요청 응답은 비동기)
				// -> REST API를 구축하기 위한 Controller
public class CommentController {

	@Autowired
	private CommentService service;
	
	// 댓글 목록 조회					// json 통신 시 한글깨짐 방지
	@GetMapping(value="/comment", produces="application/json; charset=UTF-8")
	public List<Comment> select(int boardNo) {
		
		return service.select(boardNo); 
		// 동기 시 return : forward / redirect
		// 비동기 시 return : 값 자체 
	}
	
	// 댓글 삽입
	@PostMapping("/comment")
	public int insert(@RequestBody Comment comment) {
		// 요청 데이터(JSON)
		// HttpMessageConverter가 해석 -> Java 객체(comment)에 대입
		
		return service.insert(comment);
		
	}
		
	// 댓글 삭제
	@GetMapping("/comment/delete")
	public int delete(int commentNo) {
		return service.delete(commentNo);
	}
	
	// 댓글 수정
	@PostMapping("/comment/update")
	public int update(@RequestBody Comment comment) {
		return service.update(comment);
	}
		
	

}
