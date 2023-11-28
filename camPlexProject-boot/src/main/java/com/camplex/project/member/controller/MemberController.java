package com.camplex.project.member.controller;

import java.util.List;

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
import com.camplex.project.member.model.dto.Member;
import com.camplex.project.member.model.service.MemberService;
import com.camplex.project.member.model.service.WishlistService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
@SessionAttributes({"loginMember"})
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@Autowired
	private WishlistService wishlistService;
	
	@Autowired
	private ItemService itemService;
	
	// 로그인 페이지 이동
	@GetMapping("/login")
	public String login() {
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
			
			System.out.println(loginMember);
			
			model.addAttribute("loginMember", loginMember);
		} else {
			path += referer;
			
			ra.addFlashAttribute("message", "아이디 또는 비밀번호 불일치");
		}
		
		return path;
	}
	
	@GetMapping("/signUp")
	public String signUp() {
		return "member/idPw/signUp";
	}
	
	// 회원 가입 진행
	@PostMapping("/signUp")
	public String signUp(Member inputMember, MultipartFile memberProfileInput,
						RedirectAttributes ra ) throws Exception {	
		
		System.out.println(inputMember);
		
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
	
	// 비밀번호 변경 페이지 이동
	@GetMapping("/searchPw")
	public String searchPw() {
		return "member/idPw/pwReset1";
	}
	
	// 로그아웃 이동
	@GetMapping("/logout")
	public String logout(SessionStatus status, HttpSession session) {
		status.setComplete();
		return "redirect:/";
	}
	
	// 회원 마이페이지 이동
	@GetMapping("/myPage")
	public String myPage() {
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
								MultipartFile memberProfileInput,
								RedirectAttributes ra,
								Model model
								) throws Exception {
		
		String path = "redirect:";
		String message = null;
		
		inputMember.setMemberNo(loginMember.getMemberNo());
		
		int result = service.updateMember(memberProfileInput, inputMember);
		
		if(result > 0) {
			path += "myPage";
			
			message = "정보가 수정되었습니다.";
			
			model.addAttribute("loginMember", inputMember);
			
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
								RedirectAttributes ra,
								@RequestHeader(value = "referer") String referer
								) {
		
		int memberNo = loginMember.getMemberNo();
		
		int result = service.deleteMember(memberPw, memberNo);
		
		String message = null;
		String path = "redirect:";
		
		
		if(result > 0 ) {
		
			message = "탈퇴 되었습니다.";
			path = "/member/memberWithdrawal2";
			status.setComplete();
		
		} else {
			
			message = "계정과 비밀번호가 일치하지 않습니다.";
			path += referer;
			
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
