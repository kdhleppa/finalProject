package com.camplex.project.item.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camplex.project.item.mappers.ItemMapper2;
import com.camplex.project.item.model.dto.Item;

@Service
public class ItemServiceImpl2 implements ItemService2{
	
	@Autowired
	private ItemMapper2 mapper;

	@Override
	public List<Item> searchItems() {
		
		return mapper.searchItems();
	}
}
