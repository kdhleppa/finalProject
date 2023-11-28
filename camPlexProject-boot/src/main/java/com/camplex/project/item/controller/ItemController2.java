package com.camplex.project.item.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.camplex.project.item.model.dto.Item;
import com.camplex.project.item.model.service.ItemService2;

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
		
		System.out.println(model);
		
		return "item/rentalCheckPage";
	}
	
	@GetMapping("/upload")
	public String itemUpload() {
		
		return "item/uploadRentalPage";
	}
}
