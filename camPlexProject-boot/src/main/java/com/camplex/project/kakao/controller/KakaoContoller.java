package com.camplex.project.kakao.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.camplex.project.kakao.service.KakaoService;
import com.camplex.project.member.model.dto.Member;
import com.camplex.project.paysys.model.dto.InfoForReservation;
import com.camplex.project.paysys.model.dto.RentalPaymentItem;
import com.camplex.project.paysys.model.service.PaysysService;

import jakarta.servlet.http.HttpServletRequest;
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
    
	private InfoForReservation tempInfo;
	
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
        info.setSenderName(loginMember.getMemberName());
        
        int result = service.insertKakao(info);
        
        tempPaymentNo = info.getPaymentNo();
        tempInfo = info;
        
        if (result > 0) {
        	System.out.println("결제 정보 저장 성공");
        }  
        
        return "redirect:" + kakaopay.kakaoPayReady(info);
 
    }
    
    @PostMapping("/kakaoRentalPay")
    public String kakaoPayRental(@SessionAttribute("loginMember") Member loginMember,
    		Integer price,
    		@RequestParam List<Integer> itemNo,
    		@RequestParam List<Integer> reservationNo,
		    @RequestParam List<Integer> cartItemNo,
		    @RequestParam List<Integer> rentalItemQuantity,
		    @RequestParam List<String> itemName
    		) {
    	
		int result = 0;
		int result2 = 0;
    	int size = itemNo.size() - 1;
    	List<RentalPaymentItem> rents = new ArrayList<>();
    	
    	for (int i = 0; i < itemNo.size(); i++) {
			RentalPaymentItem item = new RentalPaymentItem();
			item.setItemNo(itemNo.get(i));
	        item.setReservationNo(reservationNo.get(i));
	        item.setCartItemNo(cartItemNo.get(i));
	        item.setRentalItemQuantity(rentalItemQuantity.get(i));
	        rents.add(item);
			
		}
    	
    	
    	Map<String, Object> map = new HashMap<>();
    	map.put("memberNo", loginMember.getMemberNo());
    	map.put("price", price);
    	map.put("size", size);
    	map.put("senderName", loginMember.getMemberName());
    	InfoForReservation info = new InfoForReservation();
    	
    	if (!rents.isEmpty()) {
    		result = service.insertPayRentalKakao(map);
    		Integer rentalPaymentNo= service.selectLastInsertId();
    		info.setPaymentNo((int)rentalPaymentNo);
    		info.setPrice(price);
    		info.setSenderName(loginMember.getMemberName());
    		// 조건문으로 렌탈일경우 값 세팅
    		info.setCampDeName("렌탈");
    		// 캠프네임에 아이템네임 세팅
    		info.setCampName(itemName.get(0));
    		// 외 *개 세팅
    		info.setPriceOneDay(size);
    		tempPaymentNo = info.getPaymentNo();
    		tempInfo = info;
    		if (result > 0 ) {
    			for (RentalPaymentItem rent : rents) {
					rent.setRentalPaymentNo(rentalPaymentNo);
					result2 = service.insertPayRentalItem(rent);
					if (result2 > 0 ) {
						System.out.println("인서트페이렌탈아이템까진 문제없음");
					}
    			}
    			System.out.println("rselut :" +result);
    		}
    	}
    	
    	return "redirect:" + kakaopay.kakaoPayReadyRental(info, cartItemNo);
    }
    
    @GetMapping("/paysys/payDone")
    public String kakaoPaySuccess(@RequestParam("pg_token") String pg_token, 
    							   Model model
    								) {
        log.info("kakaoPaySuccess pg_token : " + pg_token);
        
        int result = service.updatePayState(tempPaymentNo);
        
        if(result > 0) {
        	
        	int reserveResult = service.insertReservation(tempInfo);
        	
        }
        
        model.addAttribute("payInfo", kakaopay.kakaoPayInfo(pg_token));
        
        return "/paysys/kakaoDone";
    }
    
    
    @GetMapping("/paysys/payDoneRental")
    public String kakaoPaySuccessRental(@RequestParam("pg_token") String pg_token, 
    		Model model
    		) {
    	log.info("kakaoPaySuccess pg_token : " + pg_token);
    	
    	int result = service.updatePayRentalState(tempPaymentNo);
    	
    	
    	model.addAttribute("payInfo", kakaopay.kakaoPayInfo(pg_token));
    	
    	return "/paysys/kakaoDone";
    }

}
