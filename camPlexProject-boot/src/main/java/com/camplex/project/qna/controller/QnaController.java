package com.camplex.project.qna.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.camplex.project.member.model.dto.Member;
import com.camplex.project.qna.model.dto.Qna;
import com.camplex.project.qna.model.dto.ceoQna;
import com.camplex.project.qna.model.service.QnaService;

@SessionAttributes({"loginMember"})
@RequestMapping("/qna")
@Controller
public class QnaController {
	
	@Autowired
	private QnaService service;
	
	// 1:1 문의(관리자) 리스트 이동
	@GetMapping("/qnaList")
	public String qnaList(Model model) {
		
		List<Qna> list = new ArrayList<Qna>();
		
		list = service.selectQna();
		
		model.addAttribute("list", list);
		
		return "qna/qnaList";
	}
	
	// 1:1 문의(CEO) 답변완료 리스트 이동
	@GetMapping("/qnaYList")
	public String qnaYList(Model model) {
		
		List<Qna> list = new ArrayList<Qna>();
		
		list = service.selectQnaY();
		
		model.addAttribute("list", list);
		
		return "/qna/qnaYList";
	}
	
	// 1:1 문의(관리자) 답변
	@GetMapping("/qnaAnswer")
	public String qnaAnswer(String QNAAnswer, int qnaNo, 
							RedirectAttributes ra,
							@RequestHeader(value = "referer") String referer
			) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("QNAAnswer", QNAAnswer);
		map.put("qnaNo", qnaNo);
		
		String msg = "";
		
		int result = service.insertAnswer(map);
		
		if(result > 0) {
			
			msg = "답변이 등록되었습니다.";
			
		} else {
			
			msg = "답변 등록을 실패했습니다.";
		}

		ra.addFlashAttribute("msg", msg);
		
		return "redirect:" + referer;
	}
	
	// 1:1 문의(관리자) 수정
	@GetMapping("/qnaAnswerUpdate")
	public String qnaAnswerUpdqte(String QNAAnswer, int qnaNo, RedirectAttributes ra,
									@RequestHeader(value = "referer") String referer
			) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("QNAAnswer", QNAAnswer);
		map.put("qnaNo", qnaNo);
		
		String msg = "";
		
		int result = service.updateAnswer(map);
		
		if(result > 0) {
			
			msg = "답변이 등록되었습니다.";
			
		} else {
			
			msg = "답변 등록을 실패했습니다.";
		}

		ra.addFlashAttribute("msg", msg);
		
		return "redirect:" + referer;
		
	}
	
	// 1:1 문의(관리자) 입력
	/**
	 * @param QNATitle
	 * @param QNAContent
	 * @param loginMember
	 * @param ra
	 * @param referer
	 * @return
	 */
	@PostMapping("/qnaSubmit")
	public String qnaSubmit(String QNATitle, String QNAContent,
							@SessionAttribute("loginMember") Member loginMember,
							RedirectAttributes ra,
							@RequestHeader(value = "referer") String referer
			) {
		
		String msg = "";
		
		Qna qna = new Qna();
		
		qna.setQNATitle(QNATitle);
		qna.setQNAContent(QNAContent);
		qna.setMemberNo(loginMember.getMemberNo());
		qna.setMemberNickname(loginMember.getMemberNickname());
		
		int result = service.insertQNA(qna);
		
		if(result > 0) {
			
			msg = "문의사항이 등록되었습니다.";
			
		} else {
			
			msg = "문의사항 등록이 실패했습니다.";
		}
		
		ra.addFlashAttribute("msg", msg);
		
		return "redirect:" + referer;
		
	}
	
	
	
}
