package com.camplex.project.board.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.camplex.project.board.model.dto.Board;
import com.camplex.project.board.model.dto.BoardImage;
import com.camplex.project.board.model.service.BoardService;
import com.camplex.project.member.model.dto.Member;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SessionAttributes({"loginMember"})
@RequestMapping("/board")
@Controller
public class BoardController {

	@Autowired
	private BoardService service;
	
	// 게시글 목록 조회
	@GetMapping("/{boardType:[A-Z]+}")
	public String selectBoardList(
				@PathVariable("boardType") String boardType,
				@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
				Model model,
				@RequestParam Map<String, Object> paramMap // 파라미터 전부 다담겨있음(검색 시)
			) {
		
		if( paramMap.get("key") == null ) { // 검색어가 없을 때 (검색 X)
			
			// 게시글 목록 조회 서비스 호출
			Map<String, Object> map = service.selectBoardList(boardType, cp);
			
			// 조회 결과를 request scope에 세팅 후 forward
			model.addAttribute("map", map);
			
		} else { // 검색어가 있을 때 (검색 O)
			
			paramMap.put("boardType", boardType);
			
			Map<String, Object> map = service.selectBoardList(paramMap, cp); // 오버로딩 적용
			
			model.addAttribute("map", map);
		}
		
		return "board/boardList";
	}
	
//	// 게시글 조회수 순 정렬
//	@ResponseBody
//	@GetMapping("/orderReadCount")
//	public Map<String, Object> OrderReadCount(
//				@PathVariable("boardType") String boardType,
//				@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
//				Model model,
//				@RequestParam Map<String, Object> paramMap
//			) {
//		
//		Map<String, Object> map = service.orderReadCount(paramMap, cp);
//		
//		return map;
//	}
	
	
	
	// 게시글 상세 조회
	@GetMapping("/{boardType}/{boardNo}")
	public String boardDetail(
				@PathVariable("boardType") String boardType,
				@PathVariable("boardNo") int boardNo,
				Model model, // 데이터 전달용 객체
				RedirectAttributes ra,  // 리다이렉트 시 데이터 전달 객체
				@SessionAttribute(value="loginMember", required = false) Member loginMember,
				// 세션에서 loginMember를 얻어오는데 없으면 null, 있으면 회원정보 저장
				
				// 쿠키를 이용한 조회 수 증가에서 사용
				HttpServletRequest req,
				HttpServletResponse resp
				
			) throws ParseException {
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardType", boardType);
		map.put("boardNo", boardNo);
		
		// 게시글 상세 조회 서비스 호출
		Board board = service.selectBoard(map);
		
		String path = null;
		
		if(board != null) { // 조회 결과가 있을 경우
			
			// --------------------------------------------------
			// 현재 로그인 상태인 경우
			// 로그인한 회원이 해당 게시글에 좋아요를 눌렀는지 확인
			
			// 로그인 상태인 경우
			if(loginMember != null) {
				// 회원번호를 얻어와야해요
				// map(boardCode, boardNo, memberNo)
				map.put("memberNo", loginMember.getMemberNo());
				
				
				// 좋아요 여부 확인 서비스 호출
				int result = service.boardLikeCheck(map);
				
				// 결과값을 통해 분기처리
				// 누른적이 있을 경우 처리
				// "likeCheck" 
				if(result > 0) model.addAttribute("likeCheck", "on");
				
				
			}
			
			// --------------------------------------------------
			
			// 쿠키를 이용한 조회 수 증가 처리
			
			// 1) 비회원 또는 로그인한 회원의 글이 아닌경우 
			if(loginMember == null || 
					loginMember.getMemberNo() != board.getMemberNo() ) {
				
				// 2) 쿠키 얻어오기
				Cookie c = null;
				
				// 요청에 담겨있는 모든 쿠키 얻어오기
				Cookie[] cookies = req.getCookies();
				
				if(cookies != null) { // 쿠키가 존재할 경우
					
					// 쿠키 중 "readBoardNo" 라는 쿠키를 찾아서 c에 대입
					for(Cookie cookie : cookies) {
						if(cookie.getName().equals("readBoardNo")) {
							c = cookie;
							break;
						}
					}
					
				}
				
				
				// 3) 기존 쿠키가 없거나 ( c == null ) 
				//    존재는 하나 현재 게시글 번호가 
				//    쿠키에 저장되지 않은 경우  ( 해당 게시글 본적 없음 )
				
				int result = 0;
				
				if(c == null) {
					// 쿠키가 존재 X -> 하나 새로 생성
					c = new Cookie("readBoardNo", "|" + boardNo + "|");
					
					// 조회수 증가 서비스 호출
					result = service.updateReadCount(boardNo);
					
				} else {
					// 현재 게시글 번호가 쿠키에 있는지 확인
					
					// Cookie.getValue() : 쿠키에 저장된 모든 값을 읽어옴 -> String으로 반환
					
					// String.indexOf("문자열") 
					// : 찾는 문자열이 String 몇번 인덱스에 존재하는지 반환
					// 단, 없으면 -1 반환
					
					if(c.getValue().indexOf("|" + boardNo + "|") == -1) {
						// 현재 게시글 번호가 쿠키에 없다면
						
						// 기존 값에 게시글 번호 추가해서 다시 세팅
						c.setValue( c.getValue() + "|" + boardNo + "|" );
						
						// 조회수 증가 서비스 호출
						result = service.updateReadCount(boardNo);
						
					}
				} // 3) 종료
				
				if(result > 0) {
					board.setBoardReadCount(board.getBoardReadCount() + 1);
					// 조회된 board 조회 수와 DB 조회 수 동기화
					
					// 적용 경로 설정
					c.setPath("/"); //  "/" 이하 경로 요청 시 쿠키 서버로 전달
					
					// 수명 지정
					Calendar cal = Calendar.getInstance(); // 싱글톤 패턴
					cal.add(cal.DATE, 1);
					
					// 날짜 표기법 변경 객체 (DB의 TO_CHAR()와 비슷)
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					
					// java.util.Date
					Date a = new Date(); // 현재 시간
					
					Date temp = new Date(cal.getTimeInMillis()); // 내일 (24시간 후)
					// 2023-05-11 12:16:10
					
					Date b = sdf.parse(sdf.format(temp)); // 내일 0시 0분 0초
					
					
					// 내일 0시 0분 0초 - 현재 시간
					long diff = (b.getTime()  -  a.getTime()) / 1000; 
					// -> 내일 0시 0분 0초까지 남은 시간을 초단위로 반환
					
					c.setMaxAge((int)diff); // 수명 설정
					
					resp.addCookie(c); // 응답 객체를 이용해서
									   // 클라이언트에게 전달
				}

				
			}
			
			
			// --------------------------------------------------
			
			path = "board/boardDetail"; // forward 할 jsp 경로
			model.addAttribute("board", board);
			
			// 게시글에 이미지가 있을 경우
			//if(!board.getImageList().isEmpty()) 
			if(board.getImageList().size() > 0 ) {
				
				BoardImage thumbnail = null;
				
				// 0번 인덱스 이미지의 순서가 0인 경우 == 썸네일
				if(board.getImageList().get(0).getBoardImageOrder() == 0) {
					thumbnail = board.getImageList().get(0);
				}
				
				model.addAttribute("thumbnail", thumbnail); // 썸네일 없으면 null
				
				// 썸네일 있으면 start = 1
				//        없으면 start = 0
				model.addAttribute("start", thumbnail != null ? 1 : 0);
				
			}
			
		} else { // 조회 결과가 없을 경우
			path = "redirect:/board/" + boardType;
			// 게시판 첫페이지로 리다이렉트
			
			ra.addFlashAttribute("message", "해당 게시글이 존재하지 않습니다");
			
		}
		
		return path;
		
	}
		
	// 좋아요 처리
	@PostMapping("/like")
	@ResponseBody // 반환되는 값이 비동기 요청한 곳으로 돌아가게 함
	public int like(@RequestBody Map<String, Integer> paramMap) {
		return service.like(paramMap);
	}
	
	
}
