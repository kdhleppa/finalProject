package com.camplex.project.member.controller;

import com.camplex.project.common.etc.MsgEntity;
import com.camplex.project.kakao.service.KakaoService;
import com.camplex.project.member.model.dto.KakaoDTO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("kakao")
public class KakaoController {

    private final KakaoService kakaoService;

    @GetMapping("/callback")
    public ResponseEntity<MsgEntity> callback(HttpServletRequest request) throws Exception {
        KakaoDTO kakaoInfo = kakaoService.getKakaoInfo(request.getParameter("code"));

        // DB에서 앞전에 카카오 로그인이 되었는지 조회
        
        // 조회된 결과가 있다면 loginMember 세션에 올림
        
        // 조회된 결과가 없다면 DB insert 후 로그인 상태로 메인페이지 이동
        
        return ResponseEntity.ok()
                .body(new MsgEntity("Success", kakaoInfo));
    }


}