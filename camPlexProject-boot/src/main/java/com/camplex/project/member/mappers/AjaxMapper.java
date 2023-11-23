package com.camplex.project.member.mappers;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AjaxMapper {

	// 이메일 중복 검사
	int checkEmail(String email);

	// 닉네임 중복 검사
	int checkNickname(String nickname);

}
