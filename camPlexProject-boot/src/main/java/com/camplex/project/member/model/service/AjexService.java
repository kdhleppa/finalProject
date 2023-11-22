package com.camplex.project.member.model.service;

public interface AjexService {

	// 이메일 중복 검사
	int checkEmail(String email);

	// 닉네임 중복 검사
	int checkNickname(String nickname);

}
