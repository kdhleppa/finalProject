package com.camplex.project.member.mappers;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AjaxMapper {

	// 이메일 중복 검사
	int checkEmail(String email);

	// 닉네임 중복 검사
	int checkNickname(String nickname);

	// 업데이트 인증번호
	int updateAuthKey(Map<String, String> map);

	// 인서트 인증번호
	int insertAuthKey(Map<String, String> map);

	// 인증번호 확인
	int checkAuthKey(Map<String, Object> paramMap);

}
