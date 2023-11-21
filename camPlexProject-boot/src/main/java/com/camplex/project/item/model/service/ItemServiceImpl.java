package com.camplex.project.item.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.camplex.project.item.mappers.ItemMapper;
import com.camplex.project.item.model.dto.Item;

@Service
@PropertySource("classpath:/config.properties")
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper mapper;
	
	@Override
	public Item selectDetail(int itemNo) {
		
		return mapper.selectDetail(itemNo);
	}
	
	
	
	

}
