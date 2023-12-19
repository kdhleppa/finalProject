package com.camplex.project.item.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd (E)");
		    DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 

		    rsvInfo.forEach(info -> {
		        LocalDateTime entDate = LocalDateTime.parse(info.getCampEntDate(), originalFormatter);
		        LocalDateTime outDate = LocalDateTime.parse(info.getCampOutDate(), originalFormatter);
		        info.setCampEntDate(entDate.format(formatter));
		        info.setCampOutDate(outDate.format(formatter));
		    });
			
			model.addAttribute("item", item);
			model.addAttribute("rsvInfo", rsvInfo);
			return "/item/itemDetail";
		}
	}
	
	/** 아이템 목록 정렬
	 * @return
	 */
	@GetMapping("/order")
	@ResponseBody
	public List<Item> orderItem(String category){
		
		List<Item> list = new ArrayList<>();
		
		switch(category) {
		
		case "priceLow": list = service.selectItemListPriceLow(); break;
		case "priceHigh": list = service.selectItemListPricehigh(); break;
		case "current" : list = service.selectItemList(); break;
		case "order" : list = service.selectItemListOrder(); break;
		
		}
		
		return list;
	}
	
	/** 아이템 검색
	 * @param input
	 * @return
	 */
	@ResponseBody
	@GetMapping("/search")
	public List<Item> searchItem(String input){
		
		List<Item> list = service.searchItem(input);
		
		return list;
	}
	
	/** 아이템 카테고리 분류
	 * @param category
	 * @return
	 */
	@ResponseBody
	@GetMapping("/category")
	public List<Item> selectItemCategory(String category){
		
		List<Item> list = new ArrayList<>();
		
		switch(category) {
		
		case "a": list = service.selectItemList(); break;
		case "f": list = service.selectfurnitureItemList(); break;
		case "t": list = service.selectTentItemList(); break;
		case "ba": list = service.selectBagItemList(); break;
		case "k": list = service.selectKitchenItemList(); break;
		
		}
		
		return list;
	}
	
}
