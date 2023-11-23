package com.camplex.project.member.model.service;

import org.springframework.web.multipart.MultipartFile;

import com.camplex.project.member.model.dto.Member;

public interface MemberService {

	/** 로그인 서비스
	 * @param inputMember
	 * @return
	 */
	Member loginMember(Member inputMember);

	int signUp(MultipartFile profileImg, Member inputMember) throws Exception;

	

}
