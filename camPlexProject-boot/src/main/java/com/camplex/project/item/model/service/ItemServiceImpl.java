package com.camplex.project.item.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.camplex.project.item.mappers.ItemMapper;
import com.camplex.project.item.model.dto.Item;
import com.camplex.project.item.model.dto.MembersReservationDate;

@Service
@PropertySource("classpath:/config.properties")
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper mapper;
	
	/** 상세보기 조회
	 *
	 */
	@Override
	public Item selectDetail(int itemNo) {
		
		return mapper.selectDetail(itemNo);
	}

	/** 상품상세보기로그인시예약해둔캠핑장정보조회
	 *
	 */
	@Override
	public List<MembersReservationDate> membersRsvInfo(int memberNo) {
		// TODO Auto-generated method stub
		return mapper.membersRsvInfo(memberNo);
	}

	@Override
	public List<Item> selectItemWish(int memberNo) {
		return mapper.selectItemWish(memberNo);
	}
	
	
	
	

}
