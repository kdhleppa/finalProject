package com.camplex.project.kakao.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.camplex.project.kakao.model.vo.KakaoPayApprovalVO;
import com.camplex.project.kakao.model.vo.KakaoPayReadyVO;
import com.camplex.project.paysys.model.dto.InfoForReservation;

import lombok.extern.java.Log;

@Service
@Log
public class KakaoService {

	private KakaoPayReadyVO kakaoPayReadyVO;
	private KakaoPayApprovalVO kakaoPayApprovalVO;
	
	private static final String HOST = "https://kapi.kakao.com";
	
	/** 카카오 페이
	 * @param info
	 * @return
	 */
	public String kakaoPayReady(InfoForReservation info) {
		
		 RestTemplate restTemplate = new RestTemplate();
		 
	        // 서버로 요청할 Header
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Authorization", "KakaoAK " + "a13f547e0ea960761cb3b4a00a500ca8");
	        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
	        
	        // 서버로 요청할 Body
	        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
	        params.add("cid", "TC0ONETIME");
	        params.add("partner_order_id", "camplex");
	        params.add("partner_user_id", "camplex");
	        params.add("item_name", info.getCampName()+" "+info.getCampDeName());
	        params.add("quantity", "1");
	        params.add("total_amount", info.getPrice());
	        params.add("tax_free_amount", "100");
	        params.add("approval_url","http://localhost/paysys/payDone");
	        params.add("cancel_url", "http://localhost/paysys/payCancel");
	        params.add("fail_url", "http://localhost/paysys/payFail");
		
	        HttpEntity<MultiValueMap<String, Object>> body = new HttpEntity<MultiValueMap<String, Object>>(params, headers);
		
	        try {
	            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);
	            
	            log.info("" + kakaoPayReadyVO);
	            
	            return kakaoPayReadyVO.getNext_redirect_pc_url();
	 
	        } catch (RestClientException e) {
	            e.printStackTrace();
	        } catch (URISyntaxException e) {
	            e.printStackTrace();
	        }
	        
	        return "/kakaoCancel";
	}
	
	public KakaoPayApprovalVO kakaoPayInfo(String pg_token) {
		 
        log.info("KakaoPayInfoVO............................................");
        log.info("-----------------------------");
        
        RestTemplate restTemplate = new RestTemplate();
 
        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "a13f547e0ea960761cb3b4a00a500ca8");
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        
        // 서버로 요청할 Body
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoPayReadyVO.getTid());
        params.add("partner_order_id", "camplex");
        params.add("partner_user_id", "camplex");
        params.add("pg_token", pg_token);
        
        HttpEntity<MultiValueMap<String, Object>> body = new HttpEntity<MultiValueMap<String, Object>>(params, headers);
        
        try {
            kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);
            log.info("" + kakaoPayApprovalVO);
          
            return kakaoPayApprovalVO;
        
        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        
        return null;
    }


}
