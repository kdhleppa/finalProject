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
import com.camplex.project.qna.model.service.ceoQnaService;

@SessionAttributes({"loginMember"})
@RequestMapping("/ceoQna")
@Controller
public class ceoQnaController {
	
	@Autowired
	private ceoQnaService service;
	
	// 1:1 문의(CEO) 미답변 리스트 이동
	@GetMapping("/ceoQnaList")
	public String ceoQnaList(Model model) {
		
		List<ceoQna> list = new ArrayList<ceoQna>();
		
		list = service.selectCeoQna();
		
		model.addAttribute("list", list);
		
		return "qna/ceoQnaList";
	}
	
	// 1:1 문의(CEO) 답변완료 리스트 이동
	@GetMapping("/ceoQnaYList")
	public String ceoQnaYList(Model model) {
		
		List<ceoQna> list = new ArrayList<ceoQna>();
		
		list = service.selectCeoQnaY();
		
		model.addAttribute("list", list);
		
		return "qna/ceoQnaYList";
	}
	
	
	// 1:1 문의(CEO) 답변
	@GetMapping("/ceoQnaAnswer")
	public String qnaAnswer(String ceoQnaAnswer, int ceoQnaNo, 
							RedirectAttributes ra,
							@RequestHeader(value = "referer") String referer
			) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("ceoQnaAnswer", ceoQnaAnswer);
		map.put("ceoQnaNo", ceoQnaNo);
		
		String msg = "";
		
		int result = service.insertCeoAnswer(map);
		
		if(result > 0) {
			
			msg = "답변이 등록되었습니다.";
			
		} else {
			
			msg = "답변 등록을 실패했습니다.";
		}

		ra.addFlashAttribute("msg", msg);
		
		return "redirect:" + referer;
	}
	
	// 1:1 문의(관리자) 수정
	@GetMapping("/ceoQnaAnswerUpdate")
	public String ceoQnaAnswerUpdqte(String ceoQnaAnswer, int ceoQnaNo, RedirectAttributes ra,
									@RequestHeader(value = "referer") String referer
			) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("ceoQnaAnswer", ceoQnaAnswer);
		map.put("ceoQnaNo", ceoQnaNo);
		
		String msg = "";
		
		int result = service.updateCeoAnswer(map);
		
		if(result > 0) {
			
			msg = "답변이 등록되었습니다.";
			
		} else {
			
			msg = "답변 등록을 실패했습니다.";
		}

		ra.addFlashAttribute("msg", msg);
		
		return "redirect:" + referer;
		
	}
	
	
	/** 캠핑장 1:1 문의 입력
	 * @param ceoQnaTitle
	 * @param ceoQnaContent
	 * @param loginMember
	 * @param ra
	 * @param referer
	 * @return
	 */
	@PostMapping("/ceoQnaSubmit")
	public String ceoqnaSubmit(ceoQna ceoQna,
							   @SessionAttribute("loginMember") Member loginMember,
							   RedirectAttributes ra,
							   @RequestHeader(value = "referer") String referer
			) {
		
		
		String msg = "";
		int memberNo = loginMember.getMemberNo();
		
		ceoQna.setMemberNo(memberNo);
		
		int result = service.insertCeoQna(ceoQna);
		
		if(result > 0) {
			
			msg = "문의사항이 등록되었습니다.\n답변 내역은 마이페이지에서 확인 가능합니다.";
			
		} else {
			
			msg = "문의사항 등록이 실패했습니다.";
		}
		
		ra.addFlashAttribute("message", msg);
		
		return "redirect:" + referer;
		
	}
	
	
	
}
