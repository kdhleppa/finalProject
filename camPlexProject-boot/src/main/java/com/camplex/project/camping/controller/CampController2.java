package com.camplex.project.camping.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.camplex.project.camping.model.dto.Camp;
import com.camplex.project.camping.model.dto.CampDetail;
import com.camplex.project.camping.model.service.CampService2;

@Controller
@RequestMapping("/camp2")
public class CampController2 {

	@Autowired
	private CampService2 service;
	
	@GetMapping("/uploadCamp")
	public String uploadPage() {
		
		return "camp/campingDetailUpload";
	}
	
	@PostMapping("/campInsert")
	public String campInsert(Camp camp
			, @RequestParam(value = "images", required = false) List<MultipartFile> images
			, RedirectAttributes ra) throws IllegalStateException, IOException {
		
		String[] optionArr = camp.getCampOption().split(",");
		String[] aroundArr = camp.getCampAroundView().split(",");
		
		System.out.println(optionArr[0]);
		
		if(camp.getCampOption() == null) {
			camp.setCampOption(null);
		} else {
			String addr = String.join("^^^", optionArr);
			camp.setCampOption(addr);
		}
		
		
		if(camp.getCampAroundView() == null) {
			camp.setCampAroundView(null);
		} else {
			String addr = String.join("^^^", aroundArr);
			camp.setCampAroundView(addr);
		}
		
		System.out.println(camp);
		
//		int result = service.campInsert(camp, images);
		
		
//		String message = null;
//		String path = "redirect:";
		
		
//		if(result > 0) {
//			message = "캠프장 등록 완료";
//			path += "/camp/campingDetailUpload";
//		} else {
//			message = "캠프장 등록 실패";
//			path += "campInsert";
//		}
		
//		ra.addFlashAttribute("message", message);
		
		return "camp/campingDetailUpload";
		
	}
	
	// 캠핑장 구역 insert + select
	@GetMapping("/insertDeCamp")
	public Map<String, Object> insertSelectDeCamp(CampDetail campDetail
			, @RequestParam(value = "campDeImges", required = false) List<MultipartFile> campDeImges)
					throws IllegalStateException, IOException {
		
		System.out.println(campDetail);
		
		int result = service.insertDeCamp(campDetail, campDeImges);
		
		
		List<CampDetail> list = service.selectDeCamp();
		
		Map<String, Object> map = new HashMap<>();
		
		return map;
		
	}
	
	
}
