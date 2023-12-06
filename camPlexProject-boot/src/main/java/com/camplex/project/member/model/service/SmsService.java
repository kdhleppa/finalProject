package com.camplex.project.member.model.service;

import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Message;

import org.json.simple.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class SmsService {

//	public void certifiedPhoneNumber(String userPhoneNumber, int randomNumber) {
//        String api_key = "NCSGGLQGQVSYVL1G";
//        String api_secret = "BFPEPU18IRCPMEQXLD4UEQM3FWZMPVUK";
//        Message coolsms = new Message(api_key, api_secret);
//
//        // 4 params(to, from, type, text) are mandatory. must be filled
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("to", userPhoneNumber);    // 수신전화번호
//        params.put("from", "01055350934");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
//        params.put("type", "SMS");
//        params.put("text", "sekkison 인증번호는" + "[" + randomNumber + "]" + "입니다."); // 문자 내용 입력
//        params.put("app_version", "test app 1.2"); // application name and version
//
//        try {
//            JSONObject obj = (JSONObject) coolsms.send(params);
//        } catch (Exception e) {
//        }
//    }

}
