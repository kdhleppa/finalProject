package com.camplex.project.member.mappers;

import org.apache.ibatis.annotations.Mapper;

import com.camplex.project.member.model.dto.Member;

@Mapper
public interface MemberMapper {

	Member login(Member inputMember);

	
	
}
