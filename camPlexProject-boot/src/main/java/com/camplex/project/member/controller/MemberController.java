package com.camplex.project.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.camplex.project.member.model.dto.Member;
import com.camplex.project.member.model.service.MemberService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/member")
@SessionAttributes({"loginMember"})
public class MemberController {
	
	@Autowired
	private MemberService service;

	// 로그인 페이지 이동
	@GetMapping("/login")
	public String login() {
		return "member/idPw/login";
	}
	
	// 로그인
	@PostMapping("/login")
	public String login(Member inputMember,
						Model model,
						@RequestHeader("referer") String referer,
						RedirectAttributes ra,
						HttpServletResponse resp
						) {
		
		Member loginMember = service.loginMember(inputMember);
		
		String path = "redirect:";
		
		
		if(loginMember != null) {
			path += "/";
			
			model.addAttribute("loginMember", loginMember);
		} else {
			path += referer;
			
			ra.addFlashAttribute("message", "아이디 또는 비밀번호 불일치");
		}
		
		return path;
	}
	
	@GetMapping("/signUp")
	public String signUp() {
		return "member/idPw/signUp";
	}
	
	@GetMapping("/searchId")
	public String searchId() {
		return "member/idPw/searchId1";
	}
	
	@GetMapping("/searchPw")
	public String searchPw() {
		return "member/idPw/pwReset1";
	}
}
