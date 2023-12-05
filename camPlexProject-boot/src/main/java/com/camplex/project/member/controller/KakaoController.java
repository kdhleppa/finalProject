package com.camplex.project.member.controller;

import com.camplex.project.common.etc.MsgEntity;
import com.camplex.project.kakao.service.KakaoService;
import com.camplex.project.member.model.dto.KakaoDTO;
import com.camplex.project.member.model.dto.Member;
import com.camplex.project.member.model.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("kakao")
@SessionAttributes({"loginMember"})
public class KakaoController {

    private final KakaoService kakaoService;
    
    @Autowired
    private MemberService service;

    @Autowired
    private Member member;
    
    @GetMapping("/callback")
    public ResponseEntity<Object> callback(HttpServletRequest request,
    										RedirectAttributes ra,
    										Model model) throws Exception {

    	KakaoDTO kakaoInfo = kakaoService.getKakaoInfo(request.getParameter("code"));

        String email = kakaoInfo.getEmail();
        String name = kakaoInfo.getNickname();
        
        member.setMemberEmail(email);
        member.setMemberName(name);
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("memberEmail", email);
        map.put("memberName", name);
        
        String path = "";
		String message = null;
        
        System.out.println(email);
        System.out.println(name);
        System.out.println(kakaoInfo);
        
        // DB에서 앞전에 카카오 로그인이 되었는지 조회
        String result = service.checkMember(email);
        
        System.out.println(result);
        
      
        	 if(result == null) {
             	// 조회된 결과가 없다면 DB insert 후 로그인 상태로 메인페이지 이동
             	int signUpResult = service.kakaoSignUp(map);
             	
             	if(signUpResult > 0) {
             		path = "/";
         			
             		Member loginMember = service.kakaoLoginMember(email);
             		
         			message = kakaoInfo.getNickname() + "님의 가입을 환영합니다";
                 	model.addAttribute("loginMember", loginMember);

             	} else {
             		path = "member/idPw/signUp";
         			
         			message = "카카오 회원 가입 실패로 camPlex 홈페이지에서 직접 회원가입 바랍니다.";
             	}
             	
             } else {
             	// 조회된 결과가 있다면 email 들고가서 select로 찾아 값 들고 
             	// loginMember에 넣어서loginMember 세션에 올림
             	Member loginMember = service.kakaoLoginMember(email);
             	
             	path = "/";
             	
             	message = kakaoInfo.getNickname() + "님 환영합니다";
             	model.addAttribute("loginMember", loginMember);
             }
             
     		ra.addFlashAttribute("message", message);
            
     		HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(path));
            return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
       
	       
    }


}