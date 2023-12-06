package com.camplex.project.member.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.camplex.project.member.model.service.AjaxService;

@Controller
public class AjaxController {

	@Autowired
	private AjaxService service;
	
	// 이메일 중복 검사
	@GetMapping("/dupCheck/email")
	@ResponseBody
	public int checkEmail(String email) {
		return service.checkEmail(email);
	}
	
	// 닉네임 중복 검사
	@GetMapping("/dupCheck/nickname")
	@ResponseBody
	public int checkNickname(String nickname) {
		return service.checkNickname(nickname);
	}
	
	// 이메일 인증번호 발송
	@GetMapping("/sendEmail/sendEmail")
	@ResponseBody
	public int sendEmail(String email) {
		return service.sendEmail(email, "메일 인증");
	}
	
	// 이메일 인증번호 확인
	@GetMapping("/sendEmail/checkAuthKey")
    @ResponseBody
    public int checkAuthKey(@RequestParam Map<String, Object> paramMap){

        return service.checkAuthKey(paramMap);
    }
	
	// 전화번호 중복 검사
	@GetMapping("/dupCheck/phone")
	@ResponseBody
	public int checkPhone(String phone) {
		return service.checkPhone(phone);
	}
	
	
	
}
