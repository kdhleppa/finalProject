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

	// 전화번호 중복 검사
	int checkPhone(String phone);

	// 인증번호 전송
	int sendAuthKey(Map<String, Object> map);
	int updateTelAuthKey(Map<String, Object> map);
	int insertTelAuthKey(Map<String, Object> map);

	// 전화번호 인증번호 확인
	int checkTelAuthkey(Map<String, Object> paramMap);

	// 등업 신청 확인
	int changeMember(String memberNo);




}
