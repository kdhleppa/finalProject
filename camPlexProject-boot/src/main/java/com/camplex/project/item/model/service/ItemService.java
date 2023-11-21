package com.camplex.project.item.model.service;

import java.util.List;

import com.camplex.project.item.model.dto.Item;
import com.camplex.project.item.model.dto.MembersReservationDate;

public interface ItemService {

	Item selectDetail(int itemNo);

	List<MembersReservationDate> membersRsvInfo(int memberNo);

}
