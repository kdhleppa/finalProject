package com.camplex.project.item.mappers;

import org.apache.ibatis.annotations.Mapper;

import com.camplex.project.item.model.dto.Item;

@Mapper
public interface ItemMapper {

	

	public Item selectDetail(int itemNo);
	
	
}
