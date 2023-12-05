package com.camplex.project.item.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.camplex.project.item.model.dto.FindCartItem;
import com.camplex.project.item.model.dto.Item;
import com.camplex.project.item.model.dto.MembersReservationDate;

@Mapper
public interface ItemMapper {

	

	public Item selectDetail(int itemNo);

	public List<MembersReservationDate> membersRsvInfo(int memberNo);

	public List<Item> selectItemWish(int memberNo);

	public List<FindCartItem> membersCartItem(int memberNo);

	
}
