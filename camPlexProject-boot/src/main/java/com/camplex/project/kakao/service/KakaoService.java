package com.camplex.project.kakao.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.camplex.project.kakao.model.vo.KakaoPayApprovalVO;
import com.camplex.project.kakao.model.vo.KakaoPayReadyVO;
import com.camplex.project.member.model.dto.KakaoDTO;
import com.camplex.project.paysys.mappers.PaysysMapper;
import com.camplex.project.paysys.model.dto.InfoForReservation;

import lombok.extern.java.Log;

@Service
@Log
@PropertySource("classpath:/config.properties")
public class KakaoService {

	@Autowired
	private PaysysMapper mapper;
	
	private KakaoPayReadyVO kakaoPayReadyVO;
	private KakaoPayApprovalVO kakaoPayApprovalVO;
	
	private static final String HOST = "https://kapi.kakao.com";
	
	@Value("${camplex.kakao.key}")
	private String key;
	
	@Value("${kakao.pay.approval.url}")
	private String approvalUrl;
	
	@Value("${kakao.pay.cancel.url}")
	private String cancelUrl;
	
	@Value("${kakao.pay.fail.url}")
	private String failUrl;
	
	
	/** 카카오 페이
	 * @param info
	 * @return
	 */
	public String kakaoPayReady(InfoForReservation info) {
		
		 RestTemplate restTemplate = new RestTemplate();
		 
	        // 서버로 요청할 Header
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Authorization", "KakaoAK " + key);
	        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
	        
	        // 서버로 요청할 Body
	        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
	        params.add("cid", "TC0ONETIME");
	        params.add("partner_order_id", "camplex");
	        params.add("partner_user_id", "camplex");
	        params.add("item_name", info.getCampName()+" ("+info.getCampDeName() + ")");
	        params.add("quantity", "1");
	        params.add("total_amount", info.getPrice());
	        params.add("tax_free_amount", "100");
	        params.add("approval_url", approvalUrl);
	        params.add("cancel_url", cancelUrl);
	        params.add("fail_url", failUrl);
		
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
	
	
	/** 페이 렌탈
	 * @param cartItemNo 
	 * @param map
	 * @return
	 */
	@Transactional
	public String kakaoPayReadyRental(InfoForReservation info, List<Integer> cartItemNo) {
		RestTemplate restTemplate = new RestTemplate();
		
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "a13f547e0ea960761cb3b4a00a500ca8");
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", "camplex");
        params.add("partner_user_id", "camplex");
        if (info.getPriceOneDay() > 0) {
        	params.add("item_name", info.getCampName()+"외"+info.getPriceOneDay() + "개");
        	
        } else {
        	params.add("item_name", info.getCampName());
        }
        params.add("quantity", "1");
        params.add("total_amount", info.getPrice());
        params.add("tax_free_amount", "100");
        params.add("approval_url", approvalUrl);
        params.add("cancel_url", cancelUrl);
        params.add("fail_url", failUrl);
        
        HttpEntity<MultiValueMap<String, Object>> body = new HttpEntity<MultiValueMap<String, Object>>(params, headers);
		
        try {
            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);
            
            log.info("" + kakaoPayReadyVO);
            if (!cartItemNo.isEmpty()) {
            	for (int i = 0 ; i < cartItemNo.size(); i++) {
            		int result = mapper.deleteCart(cartItemNo.get(i));
            		
            		if (result > 0 ) {
            			System.out.println("카카페성공카트삭제성공");
            		}
            	}
            	
            }
            
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

	@Value("${kakao.client.id}")
    private String KAKAO_CLIENT_ID;

    @Value("${kakao.client.secret}")
    private String KAKAO_CLIENT_SECRET;

    @Value("${kakao.redirect.url}")
    private String KAKAO_REDIRECT_URL;

    private final static String KAKAO_AUTH_URI = "https://kauth.kakao.com";
    private final static String KAKAO_API_URI = "https://kapi.kakao.com";

	// 카카오 로그인
    public String getKakaoLogin() {
        return KAKAO_AUTH_URI + "/oauth/authorize"
                + "?client_id=" + KAKAO_CLIENT_ID
                + "&redirect_uri=" + KAKAO_REDIRECT_URL
                + "&response_type=code";
    }

    // 카카오 로그인
	public KakaoDTO getKakaoInfo(String code) throws Exception {
		if (code == null) throw new Exception("Failed get authorization code");

        String accessToken = "";
        String refreshToken = "";

        try {
            HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-type", "application/x-www-form-urlencoded");

	        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	        params.add("grant_type"   , "authorization_code");
	        params.add("client_id"    , KAKAO_CLIENT_ID);
	        params.add("client_secret", KAKAO_CLIENT_SECRET);
	        params.add("code"         , code);
	        params.add("redirect_uri" , KAKAO_REDIRECT_URL);

	        RestTemplate restTemplate = new RestTemplate();
	        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

	        ResponseEntity<String> response = restTemplate.exchange(
	        		KAKAO_AUTH_URI + "/oauth/token",
	                HttpMethod.POST,
	                httpEntity,
	                String.class
	        );

	        JSONParser jsonParser = new JSONParser();
	        JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());

            accessToken  = (String) jsonObj.get("access_token");
            refreshToken = (String) jsonObj.get("refresh_token");
        } catch (Exception e) {
            throw new Exception("API call failed");
        }

        return getUserInfoWithToken(accessToken);
    }

	// 카카오 로그인
	private KakaoDTO getUserInfoWithToken(String accessToken)throws Exception {
        //HttpHeader 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader 담기
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = rt.exchange(
                KAKAO_API_URI + "/v2/user/me",
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        //Response 데이터 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj    = (JSONObject) jsonParser.parse(response.getBody());
        JSONObject account = (JSONObject) jsonObj.get("kakao_account");
        JSONObject profile = (JSONObject) account.get("profile");

        long id = (long) jsonObj.get("id");
        String email = String.valueOf(account.get("email"));
        String nickname = String.valueOf(profile.get("nickname"));

        return KakaoDTO.builder()
                    .id(id)
                    .email(email)
                    .nickname(nickname).build();
    }





}
