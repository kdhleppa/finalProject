package com.camplex.project.camping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.camplex.project.camping.model.service.CampService2;

@Controller
@RequestMapping("/camp2")
public class CampController2 {

	private CampService2 service;
	
	@GetMapping("/uploadCamp")
	public String uploadPage() {
		
		return "camp/campingDetailUpload";
	}
	
	
}
