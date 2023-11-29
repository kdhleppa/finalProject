package com.camplex.project.qna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.camplex.project.member.model.dto.Member;
import com.camplex.project.qna.model.dto.Qna;
import com.camplex.project.qna.model.service.QnaService;

@SessionAttributes({"loginMember"})
@RequestMapping("/qna")
@Controller
public class QnaController {
	
	@Autowired
	private QnaService service;
	
	
	// 1:1 문의 입력
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
