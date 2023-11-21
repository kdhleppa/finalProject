package com.camplex.project.camping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/camp")
public class CampIngCheckPageController {
	
	@GetMapping("/search")
	public String searchCamping() {
		
		return "camp/campingCheckPage";
	}
	
	@GetMapping("/detail")
	public String campDetail() {
		
		return "camp/campingDetail";
	}
	
	@GetMapping("/reservation")
	public String campReservation() {
		
		return "camp/campingReservation";
	}
}
