package com.camplex.project.member.model.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.annotation.PostConstruct;
import net.nurigo.sdk.message.service.DefaultMessageService;

public interface AjaxService {

	// 이메일 중복 검사
	int checkEmail(String email);

	// 닉네임 중복 검사
	int checkNickname(String nickname);

	// 이메일 인증번호 발송
	int sendEmail(String email, String string);

	// 이메일 인증번호 확인
	int checkAuthKey(Map<String, Object> paramMap);

	// 전화번호 중복 검사
	int checkPhone(String phone);

	// 인증번호 전송
	int sendAuthKey(String phone, int randomNumber);

	// 인증번호 확인
	int checkTelAuthkey(Map<String, Object> paramMap);

}
