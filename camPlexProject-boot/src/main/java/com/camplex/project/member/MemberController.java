package com.camplex.project.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

	@GetMapping("/login")
	public String login() {
		return "member/idPw/login";
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
