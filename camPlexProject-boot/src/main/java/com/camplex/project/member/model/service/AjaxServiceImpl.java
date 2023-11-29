package com.camplex.project.member.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.camplex.project.member.mappers.AjaxMapper;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class AjaxServiceImpl implements AjaxService {

	@Autowired
	private AjaxMapper mapper;
	
	@Autowired
	private JavaMailSender mailSender;

	// 이메일 중복 검사
	@Override
	public int checkEmail(String email) {
		return mapper.checkEmail(email);
	}

	// 닉네임 중복 검사
	@Override
	public int checkNickname(String nickname) {
		return mapper.checkNickname(nickname);
	}
	
	private String fromEmail = "qkql006@gmail.com";
	private String fromUsername = "비밀번호 재설정 인증번호";
	
	// 인증번호 난수 생성
	public String createAuthKey() {
        String key = "";
        for(int i=0 ; i< 6 ; i++) {
            
            int sel1 = (int)(Math.random() * 3); // 0:숫자 / 1,2:영어
            
            if(sel1 == 0) {
                
                int num = (int)(Math.random() * 10); // 0~9
                key += num;
                
            }else {
                
                char ch = (char)(Math.random() * 26 + 65); // A~Z
                
                int sel2 = (int)(Math.random() * 2); // 0:소문자 / 1:대문자
                
                if(sel2 == 0) {
                    ch = (char)(ch + ('a' - 'A')); // 대문자로 변경
                }
                
                key += ch;
            }
            
        }
        return key;
    }

	// 이메일 인증번호 발송
	@Override
	public int sendEmail(String email, String title) {
		
		String authKey = createAuthKey();
		
		 try {

           //인증메일 보내기
           MimeMessage mail = mailSender.createMimeMessage();
           
           // 제목
           String subject = "[Board Project]"+title+" 인증코드";
           
           // 문자 인코딩
           String charset = "UTF-8";
           
           // 메일 내용
           String mailContent 
               = "<p>comPlex 비밀번호 재설정 "+title+" 인증코드입니다.</p>"
               + "<h3 style='color:blue'>" + authKey + "</h3>";
           
           
           
           // 송신자(보내는 사람) 지정
           mail.setFrom(new InternetAddress(fromEmail, fromUsername));
           // 수신자(받는사람) 지정
           mail.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
           
           
           // 이메일 제목 세팅
           mail.setSubject(subject, charset);
           
           // 내용 세팅
           mail.setText(mailContent, charset, "html"); //"html" 추가 시 HTML 태그가 해석됨
           
           mailSender.send(mail);
           
       } catch (Exception e) {
           e.printStackTrace();
           return 0;
       }
       
       Map<String, String> map = new HashMap<String, String>();
       map.put("authKey", authKey);
       map.put("email", email);
       
       int result = mapper.updateAuthKey(map);
       
       if(result == 0) {
       	result = mapper.insertAuthKey(map);
       }

       return result;
	}

	// 이메일 인증번호 확인
	@Override
	public int checkAuthKey(Map<String, Object> paramMap) {
		return mapper.checkAuthKey(paramMap);
	}
	
	
}
