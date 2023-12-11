package com.camplex.project.paysys.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.camplex.project.camping.model.service.CampService;
import com.camplex.project.common.etc.ResponseMessage;
import com.camplex.project.item.model.dto.FindCartItem;
import com.camplex.project.item.model.dto.Item;
import com.camplex.project.item.model.dto.MembersReservationDate;
import com.camplex.project.item.model.service.ItemService;
import com.camplex.project.member.model.dto.Member;
import com.camplex.project.member.model.service.WishlistService;
import com.camplex.project.paysys.model.dto.CartItem;
import com.camplex.project.paysys.model.dto.InfoForReservation;
import com.camplex.project.paysys.model.dto.RentalPayment;
import com.camplex.project.paysys.model.dto.RentalPaymentItem;
import com.camplex.project.paysys.model.dto.rentPayList;
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
	
	@Autowired
	private WishlistService wishService;
	
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
		
		int memberNo = loginMember.getMemberNo();
		List<MembersReservationDate> rsvInfo = itemService.membersRsvInfo(memberNo);
		List<MembersReservationDate> rsvInfo2 = itemService.membersRsvInfo(memberNo);
		List<FindCartItem> cartItem = itemService.membersCartItem(memberNo);
		List<Item> wishlist = itemService.inCartWishlist(memberNo);

		
		model.addAttribute("cartItem", cartItem);
		model.addAttribute("rsvInfo", rsvInfo);
		model.addAttribute("rsvInfo2", rsvInfo2);
		model.addAttribute("wishlist", wishlist);
		
		return "/paysys/rentCart";
	}
	
	@ResponseBody
	@PostMapping("/quantityUpdateCart")
	public ResponseEntity<?> quantityUpdateCart(@RequestBody CartItem cartItem) {
	    int itemQuantity = cartItem.getItemQuantity();
	    int cartItemNo = cartItem.getCartItemNo();
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
	
	/** 무통장 입금
	 * @param info
	 * @param payBy
	 * @param bank
	 * @param senderName
	 * @param loginMember
	 * @param model
	 * @param ra
	 * @return
	 */
	@PostMapping("/payDone")
	public String paying(InfoForReservation info,
						 String payBy,
						 @SessionAttribute("loginMember") Member loginMember,
						 Model model,
						 RedirectAttributes ra
						 ) {
		
		int result = 0;
		String bank = info.getBank();
		
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
		
		return "paysys/payDoneBank";
	}
	
	@PostMapping("/payDoneRental")
	public String payRental(
			@RequestParam List<Integer> itemNo,
		    @RequestParam List<Integer> reservationNo,
		    @RequestParam List<Integer> cartItemNo,
		    @RequestParam List<Integer> rentalItemQuantity,
			String payBy,
			String bank,
			String senderName,
			Integer price,
			@SessionAttribute("loginMember") Member loginMember,
			Model model,
			HttpServletRequest request, RedirectAttributes ra) {
		String referer = request.getHeader("Referer");
		String path = "redirect:";	
		int result = 0;
		int result2 = 0;
		switch(bank) {
		
		case "toss" : bank = "토스뱅크 100001065362 최규연"; break;
		case "kb" : bank ="국민은행 00440204106870 이재경"; break;
		
		
	
		}
	
		
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
		map.put("senderName", senderName);
		
		if (!rents.isEmpty()) {
			InfoForReservation info = new InfoForReservation();
			info.setPrice(price);
			info.setSenderName(senderName);
			result = payService.insertPayRental(map);
			Integer rentalPaymentNo= payService.selectLastInsertId();
			model.addAttribute("info", info);
			model.addAttribute("bank", bank);
		
			if(result > 0) {
				for (RentalPaymentItem rent : rents) {
					rent.setRentalPaymentNo(rentalPaymentNo);
					result2 = payService.insertPayRentalItem(rent);
					if (result2 > 0 ) {
						payService.deleteCart(rent.getCartItemNo());
					}
					
				}
					
					ra.addFlashAttribute("message", "결제 신청이 완료되었습니다.");
				} else {
					ra.addFlashAttribute("message", "결제 진행 중 오류가 발생했습니다.");
					path +=referer;
					return path;
				}
		
		} else {
			
			ra.addFlashAttribute("message", "에러남(block안의값잘안드렁온듯)");
			path +=referer;
			return path;
		}
		
		
		return "paysys/payDoneBank";
	}


	@PostMapping("/rentCart/moveItemToOtherSite")
	public String moveItemSite(
			@RequestParam("reservationNo") int reservationNo,
		    @RequestParam("cartItemNo") int cartItemNo,
		    @RequestParam("itemNo") int itemNo,
			RedirectAttributes ra, HttpServletRequest request,
			@SessionAttribute("loginMember") Member loginMember
			) {
		int memberNo = loginMember.getMemberNo(); 
		int cartNo = payService.searchMembersCartNo(memberNo);
		String referer = request.getHeader("Referer");
		String path = "redirect:";	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reservationNo", reservationNo);
		map.put("cartItemNo", cartItemNo);
		map.put("itemNo", itemNo);
		map.put("cartNo", cartNo);
		int searchResult = 0;
		searchResult = payService.searchCartItem(map);
		
		
		if (searchResult > 0) {
			ra.addFlashAttribute("message", "선택한 캠핑장에 이미 있는 상품입니다.");
			path +=referer;
			return path;
		}
		
		
		
		int result = payService.moveItemSite(map);
		
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
	
	
	@PostMapping("/rentCart/deleteCart")
	public String deleteCart (@RequestParam("cartItemNo") int cartItemNo,
			RedirectAttributes ra, HttpServletRequest request
			) {
		String referer = request.getHeader("Referer");
		String path = "redirect:";
		
		int result = payService.deleteCart(cartItemNo);
		
		if (result > 0) {
			
			ra.addFlashAttribute("message", "삭제되었습니다.");
			path += referer;
			return path;
			
		} else {
			ra.addFlashAttribute("message", "삭제 실패.");
			path += referer;
			return path;
		}
		
		
		
		
	}
	
	@PostMapping("/rentCart/deleteAllCart")
	public String deleteAllCart (
			@SessionAttribute("loginMember") Member loginMember,
			RedirectAttributes ra,
			HttpServletRequest request
			) {
		int memberNo = loginMember.getMemberNo();
		String referer = request.getHeader("Referer");
		String path = "redirect:";
		
		int result = payService.deleteAllCart(memberNo);
		
		if (result > 0) {
			ra.addFlashAttribute("message", "삭제되었습니다.");
			path += referer;
			return path;
			
		} else {
			ra.addFlashAttribute("message", "삭제 실패.");
			path += referer;
			return path;
		}
	}
	
	@PostMapping("/payCheck")
	public String payAll(
			@SessionAttribute("loginMember") Member loginMember,
			RedirectAttributes ra, HttpServletRequest request,
			@RequestParam(value = "checkCartItemNo", required = false) List<Integer> checkCartItemNo,
			Model model
			) {
		
		if(loginMember == null) {
			ra.addFlashAttribute("message", "로그인 후 이용해 주세요");
			return "redirect:/member/login";
		} else {
		
		int memberNo = loginMember.getMemberNo();
		List<rentPayList> payList = new ArrayList<>();
		String referer = request.getHeader("Referer");
		String path = "redirect:";
		if (checkCartItemNo != null) {
			for (Integer cartItemNo: checkCartItemNo ) {
				rentPayList data = payService.selectCheckCart(cartItemNo, memberNo);
				
				payList.add(data);
			}
			
			model.addAttribute("rentPayList" , payList);
			
			
		} else {
			ra.addFlashAttribute("message", "선택된 상품이 없습니다.");
			path += referer;
			return path;
		}
		
		return "paysys/rentalPay";
		}
	}
	
	@PostMapping("/rentalPay/now")
	public String rentalPayNow(
			@SessionAttribute(value="loginMember", required = false)Member loginMember,
			RedirectAttributes ra,
			Model model,
			int quantity, int reservationNo, int itemNo
			) {
		
		if(loginMember == null) {
			ra.addFlashAttribute("message", "로그인 후 이용해 주세요");
			return "redirect:/member/login";
		} else {
			int memberNo = loginMember.getMemberNo();
			MembersReservationDate rsvInfo = new MembersReservationDate();
			rsvInfo = itemService.membersRsvInfo2(reservationNo);
			rentPayList data = new rentPayList();
			Item item = itemService.payNow(itemNo);
			data.setMemberNo(memberNo);
			data.setItemNo(itemNo);
			data.setItemPrice(item.getItemPrice());
			data.setItemName(item.getItemName());
			data.setCampName(rsvInfo.getCampName());
			data.setThumbnail(item.getThumbnail());
			data.setCartItemNo(0);
			data.setItemQuantity(quantity);
			data.setReservationNo(reservationNo);
			data.setCampEntdate(rsvInfo.getCampEntDate());
			data.setCampOutdate(rsvInfo.getCampOutDate());
			model.addAttribute("rentPayList", data);
			
			
			
			
			return "/paysys/rentalPay";
		}
	}
	

	
	@PostMapping("/moveWishlistToCart")
	public String moveWishlistToCart (
			int itemNo, int reservationNo,
			@SessionAttribute(value="loginMember", required = false)Member loginMember,
			RedirectAttributes ra, HttpServletRequest request
			) {
		Integer cartNo = 0;
		int result = 0;
		int searchResult = 0;
		int searchCartNo = 0;
		String referer = request.getHeader("Referer");
		String path = "redirect:";
		
		
		int memberNo = loginMember.getMemberNo();
		cartNo = payService.searchMembersCartNo(memberNo);
		if(cartNo == null) {
			payService.createCart(memberNo);
			cartNo = payService.searchMembersCartNo(memberNo);
		} 
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("itemNo", itemNo);
		map.put("reservationNo", reservationNo);
		map.put("cartNo", cartNo);
		map.put("memberNo", memberNo);
		
		searchResult = payService.searchCartItem(map);
		
		if (searchResult > 0) {
			ra.addFlashAttribute("message", "해당 예약캠핑장카트에 추가된 상품 입니다.\n카트에서 수량조정을 해주세요.");
			path += referer;
			return path;
			
		} else {
			result = payService.insertCartNoQuantity(map);
			
			if (result > 0) {
				ra.addFlashAttribute("message", "상품이 카트에 추가되었습니다.");
				wishService.deleteItemWish(memberNo, itemNo);
				path += referer;
				return path;
			} else {
				ra.addFlashAttribute("message", "카트 등록에 실패하였습니다.");
				path += referer;
				return path;
				
			}
			
		}
		
	}
	
	
	
	@PostMapping("/wishDelete")
	public String wishDelete (@SessionAttribute(value="loginMember", required = false)Member loginMember,
			RedirectAttributes ra, HttpServletRequest request,
			int itemNo
			) {
		String referer = request.getHeader("Referer");
		String path = "redirect:";
		int memberNo = loginMember.getMemberNo();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("itemNo", itemNo);
		map.put("memberNo", memberNo);
		
		int result = 0;
		
		result = wishService.wishDelete(map);
		
		if(result >0) {
			ra.addFlashAttribute("message", "삭제되었습니다.");
			path += referer;
			return path;
		} else {
			ra.addFlashAttribute("message", "삭제실패");
			path += referer;
			return path;
		}
	}
	
		
	
}
