package com.camplex.project.rental;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rental")
public class rentalTestController {
	
	@GetMapping("/search")
	public String searchRental() {
		
		return "item/rentalCheckPage";
	}
}
