package com.camplex.project.member.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.camplex.project.camping.model.dto.Camp;
import com.camplex.project.common.etc.ResponseMessage;
import com.camplex.project.item.model.dto.Item;
import com.camplex.project.item.model.service.ItemService;
import com.camplex.project.kakao.service.KakaoService;
import com.camplex.project.member.model.dto.CEOMember;
import com.camplex.project.member.model.dto.ItemInfoMypage;
import com.camplex.project.member.model.dto.Member;
import com.camplex.project.member.model.dto.MyPage;
import com.camplex.project.member.model.service.MemberService;
import com.camplex.project.member.model.service.NaverService;
import com.camplex.project.member.model.service.WishlistService;
import com.camplex.project.paysys.model.dto.Reservations;
import com.camplex.project.paysys.model.service.PaysysService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
@SessionAttributes({"loginMember", "searchId"})
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@Autowired
	private WishlistService wishlistService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private PaysysService payService;
	
	@Autowired
	private KakaoService kakaoService;
	
	@Autowired
	private NaverService naverService;
	
	// 로그인 페이지 이동
	@RequestMapping(value="/login", method= RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());
        model.addAttribute("naverUrl", naverService.getNaverLogin());
		return "member/idPw/login";
	}
	
	// 로그인
	@PostMapping("/login")
	public String login(Member inputMember,
						Model model,
						@RequestHeader("referer") String referer,
						RedirectAttributes ra
						) {
		
		Member loginMember = service.loginMember(inputMember);
		
		String path = "redirect:";
		
		
		if(loginMember != null) {
			path += "/";
			ra.addFlashAttribute("message", loginMember.getMemberNickname() + "님 환영합니다.");
			
			model.addAttribute("loginMember", loginMember);
		} else {
			path += referer;
			
			ra.addFlashAttribute("message", "아이디 또는 비밀번호 불일치");
		}
		
		return path;
	}
	
	// 회원 가입 페이지 이동
	@GetMapping("/signUp")
	public String signUp() {
		return "member/idPw/signUp";
	}
	
	// 회원 가입 진행
	@PostMapping("/signUp")
	public String signUp(Member inputMember, MultipartFile memberProfileInput,
						RedirectAttributes ra ) throws Exception {	
		
		int result = service.signUp(memberProfileInput, inputMember);
		
		
		String path = "redirect:";
		String message = null;
		
		if(result > 0) { // 가입 성공
			path += "login"; // 로그인 페이지로 // 아니면 로그인 된 상태로 메인페이지로 이동
			
			message = inputMember.getMemberNickname() + "님의 가입을 환영합니다";
			
		} else { // 가입 실패
			
			// 회원 가입 페이지
			//path += "/member/signUp"; // 절대경로
			path += "signUp"; // 상대 경로
			
			message = "회원 가입 실패";
			
		}
		
		// 리다이렉트 시 session에 잠깐 올라갔다 request로 복귀하도록 세팅
		ra.addFlashAttribute("message", message);
		
		return path;
	}
	
	// 아이디 찾기 페이지 이동
	@GetMapping("/searchId")
	public String searchId() {
		return "member/idPw/searchId1";
	}
	
	// 아이디 찾기
	@PostMapping("/searchId1")
	public String searchId1(
	        @RequestParam("inputName") String inputName,
	        @RequestParam("inputTel") String inputTel,
	        RedirectAttributes ra,
	        Model model) {

	    String path = "redirect:";
	    String message = null;

	    Map<String, String> map = new HashMap<>();
	    map.put("memberName", inputName);
	    map.put("memberPhone", inputTel);

	    String result = service.searchId(map);

	    if (result != null) {
	        path += "/member/searchId2";
	        model.addAttribute("searchId", result);
	    } else {
	        message = "일치하는 회원 정보가 없습니다.\n회원가입 후 이용 바랍니다.";
	        path += "/member/signUp";
	    }

	    ra.addFlashAttribute("message", message);

	    return path;
	}
	
	// 조회된 아이디 노출 페이지
	@GetMapping("/searchId2")
	public String searchId2() {
		return "member/idPw/searchId2";
	}
	
	// 비밀번호 변경 페이지 이동
	@GetMapping("/searchPw")
	public String searchPw() {
		return "member/idPw/pwReset1";
	}
	
	// 비밀번호 재설정 페이지 이동(이메일 정보)
	@PostMapping("/pwReset1")
	public String pwReset(String memberEmail,
							RedirectAttributes ra,
							Model model,
							@RequestHeader(value = "referer") String referer) {
		
		Member member = new Member();
		
		member.setMemberEmail(memberEmail);
		
		String path = "redirect:";
		String message = "";
		
		int result = service.searchMember(member);
		
		if(result > 0) {
			model.addAttribute("memberEmail", memberEmail);
			path = "/member/idPw/pwReset2";
		} else {
			message = "일치하는 회원 정보가 없습니다.";
			path = "redirect:" + referer;
		}
		
		ra.addFlashAttribute("message", message);
		
		return path;
	}
	
	// 비밀번호 재설정 (비밀번호 업데이트)
	@PostMapping("/pwReset2")
	public String pwReset2(Member inputMember,
							RedirectAttributes ra,
							@RequestHeader(value = "referer") String referer) {
		
		String path = "redirect:";
		String message = "";
		
		int result = service.changePw(inputMember);

		if(result > 0) {
			
			message = "비밀번호 변경이 완료되었습니다.";
			path += "/";
		
		} else {
			
			message = "비밀번호 변경에 실패하였습니다.";
			path += referer;
			
		}
		
		ra.addFlashAttribute("message", message);
		
		return path;
		
	}
	
	// 로그아웃 이동
	@GetMapping("/logout")
	public String logout(SessionStatus status, HttpSession session) {
		status.setComplete();
		return "redirect:/";
	}
	
	// 회원 마이페이지 이동
	@GetMapping("/myPage")
	public String myPage(@SessionAttribute("loginMember") Member loginMember,
							Model model
						) throws ParseException {
		
		int memberNo = loginMember.getMemberNo(); 
		
		MyPage myPageInfo =  service.selectMyPageInfo(memberNo);
		
		List<Reservations> olderReservation = new ArrayList<>();
		List<Reservations> upcomingReservation = new ArrayList<>();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date today = new Date();
		
		for(int i = 0 ; i < myPageInfo.getResList().size() ; i++) {
			
			Date tempDate = format.parse(myPageInfo.getResList().get(i).getCampOutDate());
						
			if(today.after(tempDate)) {
				
				Reservations res = new Reservations();
				res = myPageInfo.getResList().get(i);
				
				int resNo = res.getReservationNo();
				res.setItemList(service.selectItemListMypage(resNo));
				
				olderReservation.add(res);
				
								
			} else {
				
				Reservations res = new Reservations();
				res = myPageInfo.getResList().get(i);
				
				int resNo = res.getReservationNo();
				res.setItemList(service.selectItemListMypage(resNo));
				
				upcomingReservation.add(res);
				
			}
			
		}
		
		model.addAttribute("olderReservation", olderReservation);
		model.addAttribute("upcomingReservation", upcomingReservation);
		model.addAttribute("myPageInfo", myPageInfo);		
		
		return "member/myPage/myPage";
	}
	
	// CEO 마이페이지 이동
	@GetMapping("/CEOMyPage")
	public String CEOMyPage() {
		return "member/myPage/CEOMyPage";
	}
	
	// 관리자 마이페이지 이동
	@GetMapping("/managerMyPage")
	public String managerMyPage() {
		return "member/myPage/managerMyPage";
	}
	
	// 프로필 관리 페이지 이동
	@GetMapping("/infoModify")
	public String infoModify() {
		return "member/infoModify";
	}
	
	// 프로필 정보 수정
	@PostMapping("/updateMember")
	public String updateMember(@SessionAttribute("loginMember") Member loginMember,
								Member inputMember,
								@RequestParam("profileImg") MultipartFile profileImg,
								RedirectAttributes ra
								) throws Exception {
		
		String path = "redirect:";
		String message = null;
		
		inputMember.setMemberNo(loginMember.getMemberNo());
		
		if(profileImg.getSize() == 0) {
			inputMember.setMemberProfileImg(loginMember.getMemberProfileImg());
		}
		
		int result = service.updateMember(profileImg, inputMember);
		
		if(result > 0) {
			
			String checkMember = loginMember.getMemberType();
			
			if(checkMember.equals("U")) {
				path += "myPage";
				
			} else {
				
				if(checkMember.equals("C")) {
					path += "CEOMyPage";
					
				} else {
					path += "managerMyPage";
				}
			}
			
			loginMember.setMemberNickname(inputMember.getMemberNickname());
			loginMember.setMemberProfileImg(inputMember.getMemberProfileImg());
			
			message = "정보가 수정되었습니다.";
			
		} else {
			
			path += "infoModify";
			
			message = "정보를 수정 실패했습니다.";
			
		}
		
		ra.addFlashAttribute("message", message);
		
		return path;
	}
	
	// 회원 탈퇴 페이지 이동
	@GetMapping("/memberWithdrawal1")
	public String memberWithdrawal1() {
		return "member/memberWithdrawal1";
	}
	
	// 회원 탈퇴
	@PostMapping("/deleteMember")
	public String deleteMember(String memberPw,
								SessionStatus status,
								@SessionAttribute("loginMember") Member loginMember,
								@RequestHeader(value = "referer") String referer
								) {
		
		int memberNo = loginMember.getMemberNo();
		
		int result = service.deleteMember(memberPw, memberNo);
		
		String path = "redirect:";
		
		
		if(result > 0 ) {
		
			path = "/member/memberWithdrawal2";
			status.setComplete();
		
		} else {
			
			path += referer;
			
		}
		
		return path;
	}
	
	// CEO 계정 변경 신청 페이지 이동
	@GetMapping("/levelUpForm")
	public String levelUpForm() {
		return "member/levelUpForm";
	}
	
	// CEO 계정 변경 폼 전송
	@PostMapping("/levelUpFrom")
	public String levelUpFrom(@SessionAttribute("loginMember") Member loginMember,
								CEOMember inputCeoMember,
								MultipartFile tourLicenseInput,
								RedirectAttributes ra,
								@RequestHeader(value = "referer") String referer
								) throws Exception {
		
		inputCeoMember.setMemberNo(loginMember.getMemberNo());
		
		String path = "redirect:";
		String message = "";
		
		String searchForm = service.searchForm(loginMember.getMemberNo());
		
		if(searchForm == null) {
			
			int result = service.levelUpFrom(tourLicenseInput, inputCeoMember);
			
			if(result > 0) {
				message = "신청이 완료 되었습니다.";
				path += "myPage";
			} else {
				message = "신청을 실패했습니다.";
				path = "redirect:" + referer;
			}
			
		} else {
			message = "신청 내역이 존재합니다.";
			path += "myPage";
		}
		
		ra.addFlashAttribute("message", message);
		
		return path;
	}
	
	// 등업신청 페이지 이동
	@GetMapping("/levelUpFormCheck")
	public String levelUpFormCheck(Model model) {
		
		List<CEOMember> levelUpList = service.levelUpList();
		
		model.addAttribute("levelUpList", levelUpList);
		
		return "member/levelUpFormCheck";
	}
	
	// 전화번호 변경 페이지 이동
	@GetMapping("/phoneChange")
	public String phoneChangePage() {
		return "member/phoneChange";
	}
	
	// 전화번호 변경
	@PostMapping("/phoneChange")
	public String phoneChange(@SessionAttribute("loginMember") Member loginMember,
								Member inputMember,
								@RequestParam("inputTel") String inputTel,
								RedirectAttributes ra) {
		
		String path = "redirect:";
		String message = null;
		
		inputMember.setMemberNo(loginMember.getMemberNo());
		inputMember.setMemberPhone(inputTel);
		
		int result = service.phoneChange(inputMember);
		
		if(result > 0) {
			
			String checkMember = loginMember.getMemberType();
			
			if(checkMember.equals("U")) {
				path += "myPage";
				
			} else {
				
				if(checkMember.equals("C")) {
					path += "CEOMyPage";
					
				} else {
					path += "managerMyPage";
				}
			}
			
			loginMember.setMemberPhone(inputMember.getMemberPhone());
			
			message = "전화번호가 변경되었습니다.";
			
		} else {
			
			path += "phoneChange";
			
			message = "전화번호 변경을 실패했습니다.";
			
		}
		
		ra.addFlashAttribute("message", message);
		
		return path;
	}
	
	// 위시리스트 추가
	@PostMapping("/wishlist/insert")
	public String wishlistInsert(
			@SessionAttribute(value="loginMember", required = false)Member loginMember,
			RedirectAttributes ra,
			@RequestParam("itemNo") int itemNo,
			HttpServletRequest request
			) {
		
		int result = 0;
		int searchResult = 0;
		String referer = request.getHeader("Referer");
		String path = "redirect:";
		
		
		if(loginMember != null) {
			
			int memberNo = loginMember.getMemberNo();
			searchResult = wishlistService.searchWishlist(memberNo, itemNo);
			
			if (searchResult > 0) {
				
				ra.addFlashAttribute("message", "위시리스트에 추가된 상품 입니다.");
				path += referer;
				return path;
				
			} else {
				result = wishlistService.wishlistInsert(memberNo, itemNo);
				
				if (result > 0) {
				
					ra.addFlashAttribute("message", "상품이 위시리스트에 추가되었습니다.");
					path += referer;
					return path;
					
				} else {
					ra.addFlashAttribute("message", "위시리스트 등록에 실패하였습니다.");
					path += referer;
					return path;
				}
				
			}
		} else {
			ra.addFlashAttribute("message", "로그인 후 이용해 주세요.");
			path += "/member/login";
			return path;
		}
		
	}
	
	// 위시리스트 추가후 카트에서 제거
		@PostMapping("/wishlist/insert2")
		public String wishlistInsert2(
				@SessionAttribute(value="loginMember", required = false)Member loginMember,
				RedirectAttributes ra,
				@RequestParam("reservationNo") int reservationNo,
			    @RequestParam("cartItemNo") int cartItemNo,
			    @RequestParam("itemNo") int itemNo,
				HttpServletRequest request
				) {
			
			int result = 0;
			int searchResult = 0;
			String referer = request.getHeader("Referer");
			String path = "redirect:";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("itemNo", itemNo);
			map.put("reservationNo", reservationNo);
			map.put("cartItemNo", cartItemNo);
			
			if(loginMember != null) {
				
				int memberNo = loginMember.getMemberNo();
				searchResult = wishlistService.searchWishlist(memberNo, itemNo);
				
				if (searchResult > 0) {
					
					ra.addFlashAttribute("message", "위시리스트에 추가된 상품 입니다.");
					path += referer;
					return path;
					
				} else {
					result = wishlistService.wishlistInsert(memberNo, itemNo);
					
					if (result > 0) {
						payService.deleteItemcart(map);
						ra.addFlashAttribute("message", "상품이 위시리스트에 추가되었습니다.");
						path += referer;
						return path;
						
					} else {
						ra.addFlashAttribute("message", "위시리스트 등록에 실패하였습니다.");
						path += referer;
						return path;
					}
					
				}
			} else {
				ra.addFlashAttribute("message", "로그인 후 이용해 주세요.");
				path += "/member/login";
				return path;
			}
			
		}
	
	// 위시리스트로 이동~
	@GetMapping("/wishlist")
	public String wishlistFoward(
			@SessionAttribute(value="loginMember", required = false)Member loginMember,
			Model model
			) {
		// 회원넘버 가지고가서 위시리스트를 가지고 온다. 가지고온 캠프넘버로 캠핑사이트, 아이템넘버로 아이템정보를 가지고온다.
		int memberNo = loginMember.getMemberNo();
		
		List<Camp> camp = wishlistService.selectCampWish(memberNo);
		List<Item> item = itemService.selectItemWish(memberNo);
		
		model.addAttribute("camp", camp);
		model.addAttribute("item", item);
		
		return "/member/myPage/wishList";
		
	}
	
	@ResponseBody
	@DeleteMapping("/deleteCampWish/{campNo}")
	public ResponseEntity<?> deleteCampWish(
			@PathVariable("campNo") int campNo,
			@SessionAttribute(value="loginMember", required = false)Member loginMember
			){
		int memberNo = loginMember.getMemberNo();
		
		
		try {
            wishlistService.deleteCampWish(memberNo, campNo);
            return ResponseEntity.ok().body(new ResponseMessage("삭제 성공"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("삭제 실패"));
        }
		
		
		
	}
	
	
	@ResponseBody
	@DeleteMapping("/deleteItemWish/{itemNo}")
	public ResponseEntity<?> deleteItemWish(
			@PathVariable("itemNo") int itemNo,
			@SessionAttribute(value="loginMember", required = false)Member loginMember
			){
		int memberNo = loginMember.getMemberNo();
		
		
		try {
			wishlistService.deleteItemWish(memberNo, itemNo);
			return ResponseEntity.ok().body(new ResponseMessage("삭제 성공"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("삭제 실패"));
		}
		
		
		
	}
	
}
