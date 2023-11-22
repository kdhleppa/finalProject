package com.camplex.project.camping.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		String[] optionArr = camp.getCampOption().split("\\^\\^\\^");
		String[] arondViewArr = camp.getCampAroundView().split("\\^\\^\\^");
		
		Map<String, Object> optionMap = new HashMap<>();
		Map<String, Object> aroundViewMap = new HashMap<>();
		
		for(String option : optionArr) { 
			
			switch(option) {
			case "SWER" : optionMap.put(option, "공용샤워실"); break;
			case "CONV" : optionMap.put(option, "편의점/매점"); break;
			case "BBQ" : optionMap.put(option, "바베큐장"); break;			
			case "TOIL" : optionMap.put(option, "공용화장실"); break;			
			case "PARK" : optionMap.put(option, "공용주차장"); break;			
			case "POOL" : optionMap.put(option, "수영장"); break;			
			case "WIFI" : optionMap.put(option, "와이파이"); break;			
			case "SING" : optionMap.put(option, "노래방"); break;			
			case "CAFE" : optionMap.put(option, "카페"); break;			
			}
			
		}
		
		for(String aroundView : arondViewArr) { 
			
			switch(aroundView) {
			case "TREK" : aroundViewMap.put(aroundView, "등산로"); break;
			case "VALY" : aroundViewMap.put(aroundView, "계곡"); break;
			case "MOTN" : aroundViewMap.put(aroundView, "산/숲"); break;
			case "SEA" : aroundViewMap.put(aroundView, "바다"); break;
			case "RIVR" : aroundViewMap.put(aroundView, "강/호수"); break;
			case "CITY" : aroundViewMap.put(aroundView, "도심"); break;
			}	
		}
		
		model.addAttribute("option", optionMap);
		model.addAttribute("aroundView", aroundViewMap);
		model.addAttribute("camp", camp);
		
		return "camp/campingDetail";
	}
	
	@GetMapping("/reservation")
	public String campReservation() {
		
		return "camp/campingReservation";
	}
	
	@GetMapping("/uploadCamp")
	public String uploadCamp() {
		
		return "camp/campingDetailUpload";
	}
}
