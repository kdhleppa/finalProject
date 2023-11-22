package com.camplex.project.item.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
			@SessionAttribute(value="loginMember", required = false)Member loginMember,
			RedirectAttributes ra
			) {
		
		Item item= service.selectDetail(itemNo);
		
		if(loginMember == null) {
			
			model.addAttribute("item", item);
			
			return "/item/itemDetail"; 	
		} else {
			
			int memberNo = loginMember.getMemberNo();
			
			
			List<MembersReservationDate> rsvInfo;
			rsvInfo = service.membersRsvInfo(memberNo);
			
			model.addAttribute("item", item);
			model.addAttribute("rsvInfo", rsvInfo);
			return "/item/itemDetail";
		}
	}
	
	@GetMapping("/itemUpload")
	public String itemUpload() {
		
		return "item/uploadRentalPage";
	}
	
	
	
}
