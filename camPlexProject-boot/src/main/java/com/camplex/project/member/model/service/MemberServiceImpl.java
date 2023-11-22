package com.camplex.project.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.camplex.project.member.mappers.MemberMapper;
import com.camplex.project.member.mappers.WishlistMapper;
import com.camplex.project.member.model.dto.Member;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberMapper mapper;
	
	/*
	 * @Autowired private BCryptPasswordEncoder bycrypt;
	 */

	// 로그인 서비스
	@Override
	public Member loginMember(Member inputMember) {
		
		Member loginMember = mapper.login(inputMember);
		
		return loginMember;
	}



	
}
