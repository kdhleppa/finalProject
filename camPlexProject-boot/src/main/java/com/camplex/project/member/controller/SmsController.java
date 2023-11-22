package com.camplex.project.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.camplex.project.member.model.service.SmsService;

@Controller
public class SmsController {

	@Autowired
	private SmsService service;
	
	@PostMapping("/memberTelCheck")
	public @ResponseBody String memberTelCheck(@RequestParam(value="to") String to
								) {
		System.out.println("test");
		return service.memberTelCheck(to);
	}
}
