package com.camplex.project.member.mappers;

import org.apache.ibatis.annotations.Mapper;

import com.camplex.project.member.model.dto.Member;

@Mapper
public interface MemberMapper {

	// 로그인
	Member login(Member inputMember);

	// 회원가입
	int signUp(Member inputMember);

	
	
}
