package com.camplex.project.camping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/camp")
public class campIngCheckPageController {
	
	@GetMapping("/search")
	public String searchCamping() {
		
		return "camping/campingCheckPage";
	}
	
	@GetMapping("/detail")
	public String campDetail() {
		
		return "camping/campingDetail";
	}
	
	@GetMapping("/reservation")
	public String campReservation() {
		
		return "camping/campingReservation";
	}
}
