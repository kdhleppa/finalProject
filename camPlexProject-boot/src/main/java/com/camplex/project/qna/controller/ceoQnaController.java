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
import com.camplex.project.qna.model.dto.ceoQna;
import com.camplex.project.qna.model.service.ceoQnaService;

@SessionAttributes({"loginMember"})
@RequestMapping("/ceoQna")
@Controller
public class ceoQnaController {
	
	@Autowired
	private ceoQnaService service;
	
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
