package com.camplex.project.paysys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.camplex.project.camping.model.dto.Camp;
import com.camplex.project.camping.model.dto.CampDetail;
import com.camplex.project.camping.model.service.CampService;
import com.camplex.project.common.etc.ResponseMessage;
import com.camplex.project.item.model.dto.FindCartItem;
import com.camplex.project.item.model.dto.MembersReservationDate;
import com.camplex.project.item.model.service.ItemService;
import com.camplex.project.member.model.dto.Member;
import com.camplex.project.paysys.model.dto.CartItem;
import com.camplex.project.paysys.model.dto.InfoForReservation;
import com.camplex.project.paysys.model.dto.Reservations;
import com.camplex.project.paysys.model.service.PaysysService;
import com.camplex.project.paysys.model.service.ReservationsService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/paysys")
@SessionAttributes({"loginMember"})
public class PaysysController {
	
	@Autowired
	private PaysysService payService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ReservationsService rsvService;
	
	@Autowired
	private CampService campService;
	
	@PostMapping("/rentalPay/now")
	public String rentalPayNow(
			@SessionAttribute(value="loginMember", required = false)Member loginMember,
			RedirectAttributes ra,
			Model model,
			int quantity, int reservationNo, int itemNo
			) {
		if(loginMember == null) {
			ra.addFlashAttribute("message", "로그인 후 이용해 주세요");
			return "/member/idPw/login";
		} else {
		/*넘어오는값 정리
		 * 아이템정보, 수량, 예약넘버(예약지, 일정등 조회용), 로긴멤버*/
			List<CampDetail> campDetail;
			List<Camp> camp;
			List<Reservations> rsv;
			
			
			return "/paysys/rentalPay";
		}
	}
	
	@PostMapping("/rentCart/insert")
	public String rentCartInsert(int itemNo, int reservationNo, int quantity,
			@SessionAttribute(value="loginMember", required = false)Member loginMember,
			RedirectAttributes ra, HttpServletRequest request
			) {
		Integer cartNo = 0;
		int result = 0;
		int searchResult = 0;
		int searchCartNo = 0;
		String referer = request.getHeader("Referer");
		String path = "redirect:";
		
		if(loginMember != null) {
			int memberNo = loginMember.getMemberNo();
			cartNo = payService.searchMembersCartNo(memberNo);
			if(cartNo == null) {
				payService.createCart(memberNo);
				cartNo = payService.searchMembersCartNo(memberNo);
			} 
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("itemNo", itemNo);
			map.put("reservationNo", reservationNo);
			map.put("itemQuantity", quantity);
			map.put("cartNo", cartNo);
			map.put("memberNo", memberNo);
			
			searchResult = payService.searchCartItem(map);
			
			if (searchResult > 0) {
				ra.addFlashAttribute("message", "해당 예약캠핑장카트에 추가된 상품 입니다.\n카트에서 수량조정을 해주세요.");
				path += referer;
				return path;
				
			} else {
				result = payService.insertCart(map);
				
				if (result > 0) {
					ra.addFlashAttribute("message", "상품이 카트에 추가되었습니다.");
					path += referer;
					return path;
				} else {
					ra.addFlashAttribute("message", "카트 등록에 실패하였습니다.");
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
	
	
	@GetMapping("/rentCart")
	public String rentCartFoword(
			@SessionAttribute(value="loginMember", required = false)Member loginMember,
			Model model,
			RedirectAttributes ra
			) {
		
		
		if (loginMember == null) {
			ra.addFlashAttribute("message", "로그인 후 이용해 주세요.");
			return "redirect:/member/login";
		}
		
		// map = {List<MembersReservationDate>, List<장바구니리스트>}
		int memberNo = loginMember.getMemberNo();
		List<MembersReservationDate> rsvInfo = itemService.membersRsvInfo(memberNo);
		List<MembersReservationDate> rsvInfo2 = itemService.membersRsvInfo(memberNo);
		List<FindCartItem> cartItem = itemService.membersCartItem(memberNo);
		
		

		
		model.addAttribute("cartItem", cartItem);
		model.addAttribute("rsvInfo", rsvInfo);
		model.addAttribute("rsvInfo2", rsvInfo2);
		
		
		return "/paysys/rentCart";
	}
	
	@ResponseBody
	@PostMapping("/quantityUpdateCart")
	public ResponseEntity<?> quantityUpdateCart(@RequestBody CartItem cartItem) {
	    int itemQuantity = cartItem.getItemQuantity();
	    int cartItemNo = cartItem.getCartItemNo();
	    System.out.println("itemquantity"+itemQuantity);
	    System.out.println("cartItemNo"+cartItemNo);
	    int result = payService.quantityUpdateCart(cartItem);
	    if (result > 0) {
	        return ResponseEntity.ok().body(new ResponseMessage("성공"));
	    } else {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("실패"));
	    }
	}
	
	/** 캠핑장 결제 페이지 이동
	 * @param info
	 * @param model
	 * @return
	 */
	@PostMapping("/camp")
	public String payCamp(InfoForReservation info, Model model) {
		
		model.addAttribute("info", info);
		
		return "paysys/campPay";
	}
	
	@PostMapping("/payDone")
	public String paying(InfoForReservation info,
						 String payBy,
						 String bank,
						 String senderName,
						 @SessionAttribute Member loginMember,
						 Model model,
						 RedirectAttributes ra
						 ) {
		
		String path = null;
		int result = 0;
		
		// 카카오 페이
		if(payBy.equals("k")) {
			
		}
		
		// 네이버 페이
		if(payBy.equals("n")) {
			
		}
		
		// 무통장 입금
		if(payBy.equals("b")) {
			
			switch(bank) {
			
				case "toss" : bank = "토스뱅크 100001065362 최규연"; break;
				case "kb" : bank ="국민은행 00440204106870 이재경"; break;
			
			}
			
			info.setMemberNo(loginMember.getMemberNo());
			info.setPayType("무통장입금");
			
			result = payService.insertPayCamp(info);
			
			if(result > 0) {
				ra.addFlashAttribute("message", "결제 신청이 완료되었습니다.");
			} else {
				ra.addFlashAttribute("message", "결제 진행 중 오류가 발생했습니다.");
			}
			
			model.addAttribute("info", info);
			model.addAttribute("bank", bank);
			model.addAttribute("senderName", senderName);
			path = "paysys/payDoneBank";
		}
		
		
		
		
		return path;
	}


	@PostMapping("/rentCart/moveItemToOtherSite")
	public String moveItemSite(
			@RequestBody CartItem cartItem,
			RedirectAttributes ra, HttpServletRequest request
			) {
		String referer = request.getHeader("Referer");
		String path = "redirect:";	
		
		int result = payService.moveItemSite(cartItem);

		
		if (result > 0) {
			ra.addFlashAttribute("message", "상품이 이동되었습니다.");
			path += referer;
			return path;
			
		} else {
			ra.addFlashAttribute("message", "상품 이동에 실패하였습니다.");
			path += referer;
			return path;
				
		}
			
			
		
		
		
		
		
		
	}
	
	

}
