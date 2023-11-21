package com.camplex.project.camping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.camplex.project.camping.model.dto.Camp;
import com.camplex.project.camping.model.service.campService;

@Controller
@RequestMapping("/camp")
public class CampIngCheckPageController {
	
	@Autowired
	private campService service;
	
	
	/** 캠핑장 목록 조회
	 * @param model
	 * @return
	 */
	@GetMapping("/search")
	public String searchCamping(Model model) {
		
		List<Camp> campList = service.selectCampList();
		
		model.addAttribute("campList", campList);
		
		return "camp/campingCheckPage";
	}
	
	/** 캠핑장 상세 조회
	 * @param campNo
	 * @param model
	 * @return
	 */
	@GetMapping("/{campNo}")
	public String campDetail( @PathVariable("campNo") int campNo,
							  Model model			
							) {
		
		Camp camp = service.selectCampOne(campNo);
		
		model.addAttribute("camp", camp);
		
		return "camp/campingDetail";
	}
	
	@GetMapping("/reservation")
	public String campReservation() {
		
		return "camp/campingReservation";
	}
}
