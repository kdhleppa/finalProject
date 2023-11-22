package com.camplex.project.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camplex.project.member.mappers.AjexMapper;

@Service
public class AjexServiceImpl implements AjexService {

	@Autowired
	private AjexMapper mapper;

	// 이메일 중복 검사
	@Override
	public int checkEmail(String email) {
		return mapper.checkEmail(email);
	}

	// 닉네임 중복 검사
	@Override
	public int checkNickname(String nickname) {
		return mapper.checkNickname(nickname);
	}
	
	
}
