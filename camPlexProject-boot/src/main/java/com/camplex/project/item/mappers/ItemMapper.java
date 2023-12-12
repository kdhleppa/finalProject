package com.camplex.project.item.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.camplex.project.item.model.dto.FindCartItem;
import com.camplex.project.item.model.dto.Item;
import com.camplex.project.item.model.dto.MembersReservationDate;
import com.camplex.project.paysys.model.dto.rentPayList;

@Mapper
public interface ItemMapper {

	

	public Item selectDetail(int itemNo);

	public List<MembersReservationDate> membersRsvInfo(int memberNo);

	public List<Item> selectItemWish(int memberNo);

	public List<FindCartItem> membersCartItem(int memberNo);

	public List<Item> inCartWishlist(int memberNo);

	public MembersReservationDate membersRsvInfo2();

	public Item payNow(int itemNo);

	public MembersReservationDate membersRsvInfo2(int reservationNo);


	
}
