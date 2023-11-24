package com.camplex.project.paysys.model.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.camplex.project.paysys.mappers.PaysysMapper;

@Service
@PropertySource("classpath:/config.properties")
public class PaysysServiceImpl implements PaysysService{

	@Autowired
	private PaysysMapper mapper;
	
	
	@Override
	public void createCart(int memberNo) {
		mapper.createCart(memberNo);
	}


	@Override
	public int searchMembersCartNo(int memberNo) {
		return mapper.searchMembersCartNo(memberNo);
	}


	@Override
	public int insertCart(Map<String, Object> map) {
		return mapper.insertCart(map);
	}


	@Override
	public int searchCartItem(Map<String, Object> map) {
		return mapper.searchCartItem(map);
	}


	@Override
	public int searchCartNo(int memberNo) {
		return mapper.searchCartNo(memberNo);
	}

}
