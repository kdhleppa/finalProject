package com.camplex.project.member.model.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.camplex.project.common.utility.Util;
import com.camplex.project.member.mappers.MemberMapper;
import com.camplex.project.member.model.dto.Member;

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
	public int updateMember(MultipartFile memberProfileInput, Member inputMember) throws Exception {
		
		String temp = inputMember.getMemberProfileImg();
		String rename = null;
		
		if(memberProfileInput.getSize() > 0) { // 업로드된 이미지가 있을 경우
			
			// 1) 파일 이름 변경
			rename = Util.fileRename(memberProfileInput.getOriginalFilename());
			
			// 2) 바뀐 이름 loginMember에 세팅
			inputMember.setMemberProfileImg(webPath + rename);
			
		} else { // 업로드된 이미지가 없는 경우 (x버튼) 
			
			inputMember.setMemberProfileImg(null);
			
		}
		
		int result = mapper.updateMember(inputMember);
		
		if(result > 0) { // DB에 이미지 경로 업데이트 성공했다면
			
			// 업로드된 새 이미지가 있을 경우
			if(rename != null) {
				
				// 메모리에 임시 저장되어있는 파일을 서버에 진짜로 저장하는 것
				memberProfileInput.transferTo(new File(filePath + rename));
			}
			
		} else { // 실패
			
			// 이전 이미지로 프로필 다시 세팅
			inputMember.setMemberProfileImg(temp);
			
		}
		
		return result;
	}


}
