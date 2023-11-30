package com.camplex.project.member.model.service;

import org.springframework.web.multipart.MultipartFile;

import com.camplex.project.member.model.dto.CEOMember;
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

	/** 비밀번호 재설정을 위한 회원정보 찾기
	 * @param member
	 * @return
	 */
	int searchMember(Member member);

	/** 비밀번호 재설정 (비밀번호 업데이트)
	 * @param member
	 * @return
	 */
	int changePw(Member inputMember);
	
	/** CEO 변경 신청 내역 조회
	 * @param memberNo
	 * @return
	 */
	String searchForm(int memberNo);

	/** CEO 계정 변경 폼 전송
	 * @param tourLicenseInput
	 * @param inputCeoMember
	 * @return
	 */
	int levelUpFrom(MultipartFile tourLicenseInput, CEOMember inputCeoMember) throws Exception;



	

}
