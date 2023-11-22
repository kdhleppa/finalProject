package com.camplex.project.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.camplex.project.camping.model.dto.Camp;
import com.camplex.project.item.model.dto.Item;
import com.camplex.project.item.model.service.ItemService;
import com.camplex.project.member.model.dto.Member;
import com.camplex.project.member.model.service.MemberService;
import com.camplex.project.member.model.service.WishlistService;

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
						RedirectAttributes ra,
						HttpServletResponse resp
						) {
		
		Member loginMember = service.loginMember(inputMember);
		
		String path = "redirect:";
		
		
		if(loginMember != null) {
			path += "/";
			
			model.addAttribute("loginMember", loginMember);
		} else {
			path += referer;
			
			ra.addFlashAttribute("message", "아이디 또는 비밀번호 불일치");
		}
		
		return path;
	}
	
	@GetMapping("/signUp")
	public String signUp(Member inputMember,
						RedirectAttributes ra
						/*@RequestParam("profileImage") MultipartFile profileImage*/
						) {
		
		
		
		return "member/idPw/signUp";
	}
	
	@PostMapping("/signUp")
	public String signUp() {
		return null;
	}
	
	@GetMapping("/searchId")
	public String searchId() {
		return "member/idPw/searchId1";
	}
	
	@GetMapping("/searchPw")
	public String searchPw() {
		return "member/idPw/pwReset1";
	}
	
	@GetMapping("/logout")
	public String logout(SessionStatus status, HttpSession session) {
		status.setComplete();
		return "redirect:/";
	}
	
	
	@GetMapping("/myPage")
	public String myPage() {
		return "member/myPage/myPage";
	}
	
	// 위시리스트 추가
	@PostMapping("/wishlist/insert")
	public String wishlistInsert(
			@SessionAttribute(value="loginMember", required = false)Member loginMember,
			RedirectAttributes ra,
			Model model,
			int itemNo
			) {
		
		int result = 0;
		int searchResult = 0;


		
		
		if(loginMember != null) {
			
			int memberNo = loginMember.getMemberNo();
			searchResult = wishlistService.searchWishlist(memberNo, itemNo);
			
			if (searchResult > 0) {
				
				ra.addFlashAttribute("message", "위시리스트에 추가된 상품 입니다.");
				
				return "redirect:/";
				
			} else {
				
				result = wishlistService.wishlistInsert(memberNo, itemNo);
				
				if (result > 0) {
				
					ra.addFlashAttribute("message", "상품이 위시리스트에 추가되었습니다.");
					return "redirect:/";
					
				} else {
					ra.addFlashAttribute("message", "위시리스트 등록에 실패하였습니다.");
					return "redirect:/";
				}
				
			}
		} else {
			ra.addFlashAttribute("message", "로그인 후 이용해 주세요.");
			return "/member/idPw/login";
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
	
}
