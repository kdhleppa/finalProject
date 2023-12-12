package com.camplex.project.member.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.camplex.project.member.model.dto.Member;
import com.camplex.project.member.model.dto.NaverDTO;
import com.camplex.project.member.model.service.MemberService;
import com.camplex.project.member.model.service.NaverService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("naver")
@SessionAttributes({"loginMember"})
public class NaverController {

	private final NaverService naverService;

	@Autowired
	private MemberService service;

	@Autowired
	private Member member;

	@GetMapping("/callback")
	public ResponseEntity<Object> callback(HttpServletRequest request, 
											RedirectAttributes ra,
											Model model) throws Exception {

		NaverDTO naverInfo = naverService.getNaverInfo(request.getParameter("code"));

		String email = naverInfo.getEmail();
		String name = naverInfo.getName();
		String nickname = naverInfo.getNickname();
		
		// naver 간편 로그인은 010-0000-0000 이런 식으로 넘어오기 때문에
		// -를 지우기 위한 코드
		String phoneNumber = naverInfo.getMobile();
		String onlyPhoneNumber = phoneNumber.replaceAll("-", "");
		
		String phone = onlyPhoneNumber;

		member.setMemberEmail(email);
		member.setMemberName(name);
		member.setMemberNickname(nickname);
		member.setMemberPhone(phone);

		Map<String, String> map = new HashMap<String, String>();
		map.put("memberEmail", email);
		map.put("memberName", name);
		map.put("memberNickname", nickname);
		map.put("memberPhone", phone);

		String path = "";
		String message = null;

		String result = service.checkMember(email);
		
		if(result == null) {
			int signUpResult = service.naverSignUp(map);
			
			if(signUpResult > 0) {
				path = "/";
				
				Member loginMember = service.naverLoginMember(email);
				
				message = naverInfo.getNickname() + "님의 가입을 환영합니다.";
				
				model.addAttribute("loginMember", loginMember);
			} else {
				path = "member/idPw/signUp";

				message = "네이버 회원 가입 실패로 camPlex 홈페이지에서 직접 회원가입 바랍니다.";
			}
			
		} else {
			Member loginMember = service.naverLoginMember(email);

			path = "/";

			message = naverInfo.getNickname() + "님 환영합니다";
			
			model.addAttribute("loginMember", loginMember);
		}
		
		ra.addFlashAttribute("message", message);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create(path));
		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
		
	}
}
