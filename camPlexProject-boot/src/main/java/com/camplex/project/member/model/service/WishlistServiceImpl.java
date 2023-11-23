package com.camplex.project.member.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.camplex.project.camping.model.dto.Camp;
import com.camplex.project.member.mappers.WishlistMapper;

@Service
@PropertySource("classpath:/config.properties")
public class WishlistServiceImpl implements WishlistService{

	
	@Autowired
	private WishlistMapper mapper;
	
	@Transactional
	@Override
	public int wishlistInsert(int memberNo, int itemNo) {
		System.out.println("implemm:"+ memberNo);
		System.out.println("시부랄");
		return mapper.wishlistInsert(memberNo, itemNo);
	}

	/** 이미 있는 위시리스트인가 확인
	 *
	 */
	@Override
	public int searchWishlist(int memberNo, int itemNo) {
		System.out.println("개같네:" + itemNo);
		int searchResult = mapper.searchWishlist(memberNo, itemNo);
		return searchResult;
	}

	@Override
	public List<Camp> selectCampWish(int memberNo) {
		return mapper.selectCampWish(memberNo);
	}

	@Transactional
	@Override
	public void deleteCampWish(int memberNo, int campNo) {
		mapper.deleteCampWish(memberNo, campNo);
	}

	
}
