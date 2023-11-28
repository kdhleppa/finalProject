package com.camplex.project.member.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

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

	
	
}
