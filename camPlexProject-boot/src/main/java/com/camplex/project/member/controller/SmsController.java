package com.camplex.project.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.camplex.project.member.model.service.SmsService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@RestController
@RequiredArgsConstructor
public class SmsController {

	@Autowired
	private SmsService service;
	
//	@ResponseBody
//    @GetMapping("/phoneCheck")
//    // 휴대폰 인증번호
//    public String sendSMS(String phone) { // 휴대폰 문자보내기
//        int randomNumber = (int)((Math.random()* (9999 - 1000 + 1)) + 1000);//난수 생성
//        service.certifiedPhoneNumber(phone,randomNumber);
//        return Integer.toString(randomNumber);
//    }

}
