package com.camplex.project.kakao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.camplex.project.kakao.service.KakaoService;
import com.camplex.project.member.model.dto.Member;
import com.camplex.project.paysys.model.dto.InfoForReservation;
import com.camplex.project.paysys.model.service.PaysysService;

import lombok.Setter;
import lombok.extern.java.Log;

@Controller
@SessionAttributes({"loginMember"})
@Log
public class KakaoContoller {
	
	@Autowired
    private KakaoService kakaopay;
	
	@Autowired
	private PaysysService service;
    
	private int tempPaymentNo;
	
    @GetMapping("/paysys/payCancel")
    public String kakaoPayGet() {
    	int result = service.deleteKakao(tempPaymentNo);
    	
    	if (result > 0) {
        	System.out.println("결제 정보 삭제");
        } 
    	
    	return "/paysys/payCancel";
    }
    
    @PostMapping("/paysys/kakao")
    public String kakaoPay(InfoForReservation info,
    					   @SessionAttribute("loginMember") Member loginMember
    		) {
        
        info.setMemberNo(loginMember.getMemberNo());
        
        int result = service.insertKakao(info);
        
        int paymentNo = info.getPaymentNo();
        
        tempPaymentNo = paymentNo;
        
        if (result > 0) {
        	System.out.println("결제 정보 저장 성공");
        }  
        
        return "redirect:" + kakaopay.kakaoPayReady(info);
 
    }
    
    @GetMapping("/paysys/payDone")
    public String kakaoPaySuccess(@RequestParam("pg_token") String pg_token, 
    							   Model model
    								) {
        log.info("kakaoPaySuccess pg_token : " + pg_token);
        
        int result = service.updatePayState(tempPaymentNo);
        
        if(result > 0) {
        	System.out.println("결제 완료");
        }
        
        model.addAttribute("payInfo", kakaopay.kakaoPayInfo(pg_token));
        
        return "/paysys/kakaoDone";
    }

}
