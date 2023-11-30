package com.camplex.project.member.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import com.camplex.project.member.model.dto.CEOMember;
import com.camplex.project.member.model.dto.Member;

@Mapper
public interface MemberMapper {

	// 로그인
	Member login(Member inputMember);

	// 회원가입
	int signUp(Member inputMember);

	// 회원 비밀번호 조회
	String selectEncPw(int memberNo);

	// 회원 탈퇴
	int deleteMember(int memberNo);

	// 프로필 정보 수정
	int updateMember(Member inputMember);

	// 비밀번호 재설정을 위한 회원정보 찾기
	int searchMember(Member member);
	
	// 비밀번호 재설정 (비밀번호 업데이트)
	int changePw(Member inputMember);

	// CEO 변경 신청 내역 조회
	String searchForm(int memberNo);
	
	// CEO 계정 변경 폼 전송
	int levelUpFrom(CEOMember inputCeoMember);


	
	
}
