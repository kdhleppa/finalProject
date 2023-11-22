package com.camplex.project.member.model.service;

import com.camplex.project.member.model.dto.Member;

public interface MemberService {

	/** 로그인 서비스
	 * @param inputMember
	 * @return
	 */
	Member loginMember(Member inputMember);

	

}
