package com.camplex.project.member.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import com.camplex.project.member.model.dto.CEOMember;
import com.camplex.project.member.model.dto.ItemInfoMypage;
import com.camplex.project.member.model.dto.Member;
import com.camplex.project.member.model.dto.MyPage;
import com.camplex.project.member.model.dto.Wishlist;
import com.camplex.project.paysys.model.dto.Payment;
import com.camplex.project.qna.model.dto.Qna;
import com.camplex.project.qna.model.dto.ceoQna;

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

	// 마이페이지 값 뿌리기
	MyPage selectMyPageInfo(int memberNo);

	// 카카오 로그인 시 회원인지 확인
	String checkMember(String email);

	// 회원 아닐시 카카오 자동 회원가입
	int kakaoSignUp(Map<String, String> map);

	// 카카오 계정 이메일로 로그인
	Member kakaoLoginMember(String email);

	// 회원 아닐 시 네이버 자동 회원가입
	int naverSignUp(Map<String, String> map);
	
	// 네이버 계정 이메일로 로그인
	Member naverLoginMember(String email);

	// 아이디 찾기
	String searchId(Map<String, String> map);

	// 등업신청 리스트 가져오기
	List<CEOMember> levelUpList();

	/** 마이페이지 아이템 정보 가져오기
	 * @param resNo
	 * @return
	 */
	List<ItemInfoMypage> selectItemListMypage(int resNo);


	// 전화번호 변경
	int phoneChange(Member inputMember);

	/** 마이페이지 관리자 qna 목록 불러오기
	 * @param memberNo
	 * @return
	 */
	List<Qna> selectQna(int memberNo);

	List<ceoQna> selectCeoQna(int memberNo);

	// 등업 신청 카운트
	int levelUpFormCount();







	
	
}
