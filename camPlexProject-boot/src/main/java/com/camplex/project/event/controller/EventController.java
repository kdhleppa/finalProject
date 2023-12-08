package com.camplex.project.event.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@RequestMapping("/event")
@SessionAttributes({"loginMember"})
public class EventController {
	
	
	// 이벤트 목록 조회
	@GetMapping("/eventList") 
	public String eventList() {
		return"/event/eventList";
	}
	
	// 이벤트 쿠폰 페이지
	@GetMapping("/eventCoupon") 
	public String eventCoupon() {
		return"/event/eventCoupon";
	}
	
	// 이벤트 룰렛 페이지
	@GetMapping("/eventRoulette") 
	public String eventRoulette() {
		return"/event/eventRoulette";
	}
	
	// 이벤트 뽑기 페이지
	@GetMapping("/eventScratch") 
	public String eventScratch() {
		return"/event/eventScratch";
	}
	
}
