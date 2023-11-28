package com.camplex.project.member.model.service;

import java.util.Map;

public interface AjaxService {

	// 이메일 중복 검사
	int checkEmail(String email);

	// 닉네임 중복 검사
	int checkNickname(String nickname);

	// 이메일 인증번호 발송
	int sendEmail(String email, String string);

	// 이메일 인증번호 확인
	int checkAuthKey(Map<String, Object> paramMap);

}
