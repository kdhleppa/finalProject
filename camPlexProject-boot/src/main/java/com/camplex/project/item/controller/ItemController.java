package com.camplex.project.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.camplex.project.item.model.service.ItemService;

@Controller
@RequestMapping("/item")
@PropertySource("classpath:/config.properties")
public class ItemController {
	
	@Autowired
	ItemService service;
	
	@GetMapping("/itemDetail")
	public String itemDetail() {
		return "/item/itemDetail";
	}
	
}
