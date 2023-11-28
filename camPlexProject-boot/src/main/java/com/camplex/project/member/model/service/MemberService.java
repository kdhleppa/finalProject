package com.camplex.project.member.model.service;

import org.springframework.web.multipart.MultipartFile;

import com.camplex.project.member.model.dto.Member;

public interface MemberService {

	/** 로그인 서비스
	 * @param inputMember
	 * @return
	 */
	Member loginMember(Member inputMember);

	/** 회원가입 서비스
	 * @param memberProfileImg
	 * @param inputMember
	 * @return
	 * @throws Exception
	 */
	int signUp(MultipartFile memberProfileImg, Member inputMember) throws Exception;

	/** 회원탈퇴 서비스
	 * @param memberPw
	 * @param memberNo
	 * @return
	 */
	int deleteMember(String memberPw, int memberNo);

	/** 프로필 정보 수정 서비스
	 * @param memberProfileInput
	 * @param inputMember
	 * @return
	 */
	int updateMember(MultipartFile memberProfileInput, Member inputMember) throws Exception;

	

}
