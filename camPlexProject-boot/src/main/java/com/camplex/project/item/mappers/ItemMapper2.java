package com.camplex.project.item.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.camplex.project.item.model.dto.Item;

@Mapper
public interface ItemMapper2 {

	List<Item> searchItems();

}
