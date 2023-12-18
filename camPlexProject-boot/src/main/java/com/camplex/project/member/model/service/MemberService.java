package com.camplex.project.member.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.camplex.project.member.model.dto.CEOMember;
import com.camplex.project.member.model.dto.ItemInfoMypage;
import com.camplex.project.member.model.dto.Member;
import com.camplex.project.member.model.dto.MyPage;
import com.camplex.project.member.model.dto.Wishlist;
import com.camplex.project.paysys.model.dto.Payment;
import com.camplex.project.paysys.model.dto.Reservations;
import com.camplex.project.qna.model.dto.Qna;
import com.camplex.project.qna.model.dto.ceoQna;

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
	int updateMember(MultipartFile profileImg, Member inputMember) throws Exception;

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

	// 마이페이지 값 뿌리기
	MyPage selectMyPageInfo(int memberNo);

	// 카카오,네이버 로그인 시 회원인지 확인
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

	/**마이페이지 관리자 qna 목록 불러오기
	 * @param memberNo
	 * @return
	 */
	List<Qna> selectQna(int memberNo);

	/** 마이페이지 ceo qna 목록 불러오기
	 * @param memberNo
	 * @return
	 */
	List<ceoQna> selectCeoQna(int memberNo);

	// 등업 신청 카운트
	int levelUpFormCount();

	/** 캠핑 위시리스트
	 * @param memberNo
	 * @return
	 */
	List<Wishlist> selectCampWish(int memberNo);

	/** 아이템 위시리스트
	 * @param memberNo
	 * @return
	 */
	List<Wishlist> selectItemWish(int memberNo);

	Qna selectQnaOne(int qnaNo);

	// 멤버 넘버로 예약 내역 관리 조회
	List<Map<String, Object>> selectReservationList(Member member);

	// 예약 내역 아이템 리스트 조회
	List<ItemInfoMypage> selectItemList(int ss);








	

}
