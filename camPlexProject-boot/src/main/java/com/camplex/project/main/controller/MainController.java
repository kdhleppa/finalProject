package com.camplex.project.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {
	
	@RequestMapping("/")
	public String mainForward() {
	
		return "common/main";
	}
	
	@GetMapping("/loginError")
	public String loginError(RedirectAttributes ra) {
		
		ra.addFlashAttribute("message", "로그인 후 이용해주세요");
		
		return "redirect:/";
	}
	
	@GetMapping("/managerError")
	public String managerError(RedirectAttributes ra) {
		
		ra.addFlashAttribute("message", "관리자만 접근이 가능합니다.");
		
		return "redirect:/";
	}
	
	@GetMapping("/CEOError")
	public String CEOError(RedirectAttributes ra) {
		
		ra.addFlashAttribute("message", "CEO만 접근이 가능합니다.");
		
		return "redirect:/";
	}
}
