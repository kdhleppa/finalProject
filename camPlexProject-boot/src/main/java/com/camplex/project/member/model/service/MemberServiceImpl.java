package com.camplex.project.member.model.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.camplex.project.common.utility.Util;
import com.camplex.project.member.mappers.MemberMapper;
import com.camplex.project.member.model.dto.CEOMember;
import com.camplex.project.member.model.dto.ItemInfoMypage;
import com.camplex.project.member.model.dto.Member;
import com.camplex.project.member.model.dto.MyPage;
import com.camplex.project.member.model.dto.Wishlist;
import com.camplex.project.paysys.model.dto.Payment;

@Service
@PropertySource("classpath:/config.properties")
public class MemberServiceImpl implements MemberService {

	@Value("${camplex.member.location}")
	private String filePath;
	
	@Value("${camplex.member.webpath}")
	private String webPath;
	
	@Autowired
	private MemberMapper mapper;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	// 로그인 서비스
	@Override
	public Member loginMember(Member inputMember) {

		Member loginMember = mapper.login(inputMember);
		
		if(loginMember != null) {
			
			if(bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) {
				
				loginMember.setMemberPw(null);
			
			} else { 
				loginMember = null;
			}

		}

		return loginMember;
	}

	// 회원가입
	@Override
	public int signUp(MultipartFile memberProfileImg, Member inputMember) throws Exception {
		
		String encPw = bcrypt.encode(inputMember.getMemberPw());
		inputMember.setMemberPw(encPw);
		
		String temp = inputMember.getMemberProfileImg();
		String rename = null;
		
		if(memberProfileImg.getSize() > 0) { // 업로드된 이미지가 있을 경우
			
			// 1) 파일 이름 변경
			rename = Util.fileRename(memberProfileImg.getOriginalFilename());
			
			// 2) 바뀐 이름 loginMember에 세팅
			inputMember.setMemberProfileImg(webPath + rename);
			
		} else { // 업로드된 이미지가 없는 경우 (x버튼) 
			
			inputMember.setMemberProfileImg(null);
			
		}
		
		int result = mapper.signUp(inputMember);
		
		if(result > 0) { // DB에 이미지 경로 업데이트 성공했다면
			
			// 업로드된 새 이미지가 있을 경우
			if(rename != null) {
				
				// 메모리에 임시 저장되어있는 파일을 서버에 진짜로 저장하는 것
				memberProfileImg.transferTo(new File(filePath + rename));
			}
			
		} else { // 실패
			
			// 이전 이미지로 프로필 다시 세팅
			inputMember.setMemberProfileImg(temp);
			
		}
		
		return result;
	}

	// 회원탈퇴
	@Override
	public int deleteMember(String memberPw, int memberNo) {
		
		String encPw = mapper.selectEncPw(memberNo);
		
		if(bcrypt.matches(memberPw, encPw)) {
			return mapper.deleteMember(memberNo);
		}
		
		return 0;
	}

	// 프로필 정보 수정
	@Override
	public int updateMember(MultipartFile profileImg, Member inputMember) throws Exception {
		
		String temp = inputMember.getMemberProfileImg();
		String rename = null;
		
		if(profileImg.getSize() > 0) { // 업로드된 이미지가 있을 경우
			
			// 1) 파일 이름 변경
			rename = Util.fileRename(profileImg.getOriginalFilename());
			
			// 2) 바뀐 이름 loginMember에 세팅
			inputMember.setMemberProfileImg(webPath + rename);
			
		}
		
		int result = mapper.updateMember(inputMember);
		
		if(result > 0) { // DB에 이미지 경로 업데이트 성공했다면
			
			// 업로드된 새 이미지가 있을 경우
			if(rename != null) {
				
				// 메모리에 임시 저장되어있는 파일을 서버에 진짜로 저장하는 것
				profileImg.transferTo(new File(filePath + rename));
			}
			
		} else { // 실패
			
			// 이전 이미지로 프로필 다시 세팅
			inputMember.setMemberProfileImg(temp);
			
		}
		
		return result;
	}

	// 비밀번호 재설정을 위한 회원정보 찾기
	@Override
	public int searchMember(Member member) {
		return mapper.searchMember(member);
	}
	
	// 비밀번호 재설정 (비밀번호 업데이트)
	@Override
	public int changePw(Member inputMember) {
		
		String encPw = bcrypt.encode(inputMember.getMemberPw());
		
		inputMember.setMemberPw(encPw);
		
		return mapper.changePw(inputMember);
	}

	// CEO 변경 신청 내역 조회
	@Override
	public String searchForm(int memberNo) {
		return mapper.searchForm(memberNo);
	}
	
	// CEO 계정 변경 폼 전송
	@Override
	public int levelUpFrom(MultipartFile tourLicenseInput, CEOMember inputCeoMember) throws Exception {
		
		String temp = inputCeoMember.getTourLicense();
		String rename = null;
		
		if(tourLicenseInput.getSize() > 0) { // 업로드된 이미지가 있을 경우
			
			rename = Util.fileRename(tourLicenseInput.getOriginalFilename());
			
			inputCeoMember.setTourLicense(rename);
			
		}
		
		int result = mapper.levelUpFrom(inputCeoMember);
		
		if(result > 0) {
			
			if(rename != null) {
				
				tourLicenseInput.transferTo(new File(filePath + rename));
			}
			
		}
		
		return result;
	}

	// 마이페이지 값 뿌리기
	@Override
	public MyPage selectMyPageInfo(int memberNo) {
		return mapper.selectMyPageInfo(memberNo);
	}
	
	// 카카오 로그인 시 회원인지 확인
	@Override
	public String checkMember(String email) {
		return mapper.checkMember(email);
	}

	// 회원 아닐시 카카오 자동 회원가입
	@Override
	public int kakaoSignUp(Map<String, String> map) {
		return mapper.kakaoSignUp(map);
	}

	// 카카오 계정 이메일로 로그인
	@Override
	public Member kakaoLoginMember(String email) {
		return mapper.kakaoLoginMember(email);
	}

	// 회원 아닐 시 네이버 자동 회원가입
	@Override
	public int naverSignUp(Map<String, String> map) {
		return mapper.naverSignUp(map);
	}

	// 네이버 계정 이메일로 로그인
	@Override
	public Member naverLoginMember(String email) {
		return mapper.naverLoginMember(email);
	}

	// 아이디 찾기
	@Override
	public String searchId(Map<String, String> map) {
		return mapper.searchId(map);
	}

	// 등업신청 리스트 가져오기
	@Override
	public List<CEOMember> levelUpList() {
		return mapper.levelUpList();
	}

	/** 마이페이지 아이템 정보 가져오기
	 *
	 */
	@Override
	public List<ItemInfoMypage> selectItemListMypage(int resNo) {
		return mapper.selectItemListMypage(resNo);
	}








}
