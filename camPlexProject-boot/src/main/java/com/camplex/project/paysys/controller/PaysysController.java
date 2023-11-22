package com.camplex.project.paysys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.camplex.project.camping.model.dto.Camp;
import com.camplex.project.camping.model.dto.CampDetail;
import com.camplex.project.camping.model.service.CampService;
import com.camplex.project.item.model.service.ItemService;
import com.camplex.project.member.model.dto.Member;
import com.camplex.project.paysys.model.dto.Reservations;
import com.camplex.project.paysys.model.service.PaysysService;
import com.camplex.project.paysys.model.service.ReservationsService;

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
			@SessionAttribute(value="loginMember", required = false)Member loginMember
			 
			) {
		
		
		return null;
	}

}
