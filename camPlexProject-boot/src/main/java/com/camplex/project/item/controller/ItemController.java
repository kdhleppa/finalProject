package com.camplex.project.item.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.camplex.project.item.model.dto.Item;
import com.camplex.project.item.model.dto.MembersReservationDate;
import com.camplex.project.item.model.service.ItemService;
import com.camplex.project.member.model.dto.Member;

@Controller
@RequestMapping("/item")
@SessionAttributes({"loginMember"})
public class ItemController {
	
	@Autowired
	private ItemService service;
	
	@GetMapping("/itemDetail/{itemNo}")
	public String itemDetail(
			@PathVariable("itemNo") int itemNo,
			Model model,
			@SessionAttribute(value="loginMember", required = false)Member loginMember
			) {
			
		Item item= service.selectDetail(itemNo);
		int memberNo = loginMember.getMemberNo();
		
		
		List<MembersReservationDate> rsvInfo;
		rsvInfo = service.membersRsvInfo(memberNo);
		
		model.addAttribute("item", item);
		model.addAttribute("rsvInfo", rsvInfo);
		return "/item/itemDetail";
	}
	
}
