package com.camplex.project.item.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.camplex.project.item.model.dto.Item;
import com.camplex.project.item.model.service.ItemService2;

import oracle.jdbc.proxy.annotation.Post;

@Controller
@RequestMapping("/item2")
public class ItemController2 {

	@Autowired
	private ItemService2 service;
	
	@GetMapping("/search")
	public String searchItems(Model model) {
		
		List<Item> searchList = new ArrayList<Item>();
		
		searchList = service.searchItems();
		
		model.addAttribute("searchList", searchList);
		
		return "item/rentalCheckPage";
	}
	
	@GetMapping("/uploadForward")
	public String uploadForward() {
		
		return "item/uploadRentalPage";
	}
	
	
	
	/** 상품 삭제(업뎃)
	 * @param itemNo
	 * @param ra
	 * @param referer
	 * @return
	 */
	@GetMapping("/deleteItem") 
	public String deleteItem(int itemNo
			, RedirectAttributes ra
			, @RequestHeader("referer") String referer) {
	  
		int delImgResult = service.deleteItem(itemNo);
		
		String path = "redirect:";
		String message = null;
		
		if(delImgResult > 0) {
			path += "/item2/search";
			message = "삭제 완료되었습니다.";
		} else {
			path += referer;
			message = "삭제 실패했습니다.";
		}
		
		ra.addFlashAttribute("message", message);
		
		return path;
		
	}
	 
	@PostMapping("/upload")
	public String uploadItem(@RequestParam(value = "itemImages", required = false) List<MultipartFile> itemImages
			, Item item
			, RedirectAttributes ra
			, @RequestHeader("referer") String referer)
			throws IllegalStateException, IOException {
		
		System.out.println("item ::" + item);
		System.out.println("itemImages" + itemImages);
		
		int result = service.uploadItem(item, itemImages);
		
		String message = null;
		String path = "redirect:";
		
		if(result > 0) {
			message = "상품 등록 완료.";
			path += "/item2/search";
		} else {
			message = "상품 등록 실패.";
			path += referer;
		}
		
		ra.addFlashAttribute(message);
		
		
		return path;
	}
	
	@GetMapping("/editForward")
	public String editItemForward(int itemNo, Model model) {
		
		Item item = service.selectItemOfItemNo(itemNo);
		
		System.out.println("item ::" + item);
		
		model.addAttribute("item", item);
		
		return "item/editRentalPage";
	}
	
	@PostMapping("/edit")
	public String editItem(Item item
			, @RequestParam(value="itemImages", required = false) List<MultipartFile> images
			, RedirectAttributes ra
			, @RequestHeader("referer") String referer) throws IllegalStateException, IOException {
		
		System.out.println("item ::" + item);
		
		int result = service.editItem(item, images);
		
		String message = null;
		String path = "redirect:";
		
		if(result > 0) {
			message = "수정 성공!";
			path += "/item2/search";
		} else {
			message = "수정 실패...";
			path += referer;
		}
		
		ra.addAttribute("message", message);
		
		return path;
	}
	
	
}
