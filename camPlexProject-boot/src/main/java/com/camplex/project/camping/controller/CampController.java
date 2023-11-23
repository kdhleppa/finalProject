package com.camplex.project.camping.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.camplex.project.camping.model.dto.Camp;
import com.camplex.project.camping.model.dto.CampDetail;
import com.camplex.project.camping.model.dto.CampDetailImage;
import com.camplex.project.camping.model.service.CampService;
import com.camplex.project.member.model.dto.Member;

@Controller
@RequestMapping("/camp")
@SessionAttributes({"loginMember"})
public class CampController {
	
	@Autowired
	private CampService service;
	
	
	/** 캠핑장 목록 조회
	 * @param model
	 * @return
	 */
	@GetMapping("/search")
	public String searchCamping(Model model) {
		
		List<Camp> campList = service.selectCampList();
		
		model.addAttribute("campList", campList);
		
		return "camp/campingCheckPage";
	}
	
	/** 캠핑장 상세 조회
	 * @param campNo
	 * @param model
	 * @return
	 */
	@GetMapping("/{campNo}")
	public String campDetail( @PathVariable("campNo") int campNo,
							  Model model
							) {
		
		Camp camp = service.selectCampOne(campNo);
		
		String[] optionArr = camp.getCampOption().split("\\^\\^\\^");
		String[] arondViewArr = camp.getCampAroundView().split("\\^\\^\\^");
		
		Map<String, Object> optionMap = new HashMap<>();
		Map<String, Object> aroundViewMap = new HashMap<>();
		
		for(String option : optionArr) { 
			
			switch(option) {
			case "SWER" : optionMap.put(option, "공용샤워실"); break;
			case "CONV" : optionMap.put(option, "편의점/매점"); break;
			case "BBQ" : optionMap.put(option, "바베큐장"); break;			
			case "TOIL" : optionMap.put(option, "공용화장실"); break;			
			case "PARK" : optionMap.put(option, "공용주차장"); break;			
			case "POOL" : optionMap.put(option, "수영장"); break;			
			case "WIFI" : optionMap.put(option, "와이파이"); break;			
			case "SING" : optionMap.put(option, "노래방"); break;			
			case "CAFE" : optionMap.put(option, "카페"); break;			
			}
			
		}
		
		for(String aroundView : arondViewArr) { 
			
			switch(aroundView) {
			case "TREK" : aroundViewMap.put(aroundView, "등산로"); break;
			case "VALY" : aroundViewMap.put(aroundView, "계곡"); break;
			case "MOTN" : aroundViewMap.put(aroundView, "산/숲"); break;
			case "SEA" : aroundViewMap.put(aroundView, "바다"); break;
			case "RIVR" : aroundViewMap.put(aroundView, "강/호수"); break;
			case "CITY" : aroundViewMap.put(aroundView, "도심"); break;
			}	
		}
		
		model.addAttribute("option", optionMap);
		model.addAttribute("aroundView", aroundViewMap);
		model.addAttribute("camp", camp);
		
		return "camp/campingDetail";
	}
	
	/** 예약 이동
	 * @param campDeNo
	 * @param model
	 * @return
	 */
	@GetMapping("/reservation")
	public String campReservation(int campDeNo, 
								  Model model,
								  @SessionAttribute(required=false) Member loginMember,
								  RedirectAttributes ra,
								  @RequestHeader("referer") String referer
			) {
		
		 String path = "";
			 
		 
		if(loginMember == null) {
			
			ra.addFlashAttribute("message", "로그인 후 이용해주세요.");
			path = "redirect:" + referer;
			
		} else {
			
			CampDetail campDetail = service.selectCampDetail(campDeNo);
			
			List<CampDetailImage> campDetailImages = service.selectCampDetailImageList(campDeNo);
			
			model.addAttribute("campDetail", campDetail);
			model.addAttribute("campDetailImages", campDetailImages);
			
			path = "camp/campingReservation";
			
		}
		
		return path;
	}
	
	/** 위시리스트 추가
	 * @return
	 */
	@GetMapping("/wishlist")
	public String wishList(RedirectAttributes ra,
						   @SessionAttribute(value="loginMember", required=false) Member loginMember,
						   @RequestHeader("referer") String referer,
						   int campNo
			) {
			
		
		if(loginMember != null) {

			int result = 0;
			int memberNo = loginMember.getMemberNo();
			
			Map<String, Object> map = new HashMap<>();
			
			map.put("campNo", campNo);
			map.put("memberNo", memberNo);
			
			// 위시리스트에 있는지 먼저 중복 검사
			int count = service.checkDupCampWish(map);
			
			if(count > 0) {
				
				ra.addFlashAttribute("message", "이미 등록된 캠핑장입니다.");
				
			} else {
				
				result = service.insertWishlist(map);
				
				if(result > 0) {
					
					ra.addFlashAttribute("message", "위시리스트에 등록되었습니다.");
					
				} else {
					
					ra.addFlashAttribute("message", "위시리스트 등록에 실패하였습니다.");
				}
				
			}
			
		} else {
			
			ra.addFlashAttribute("message", "로그인 후 이용해주세요");
			
		}
		
		
		return "redirect:"+ referer;
	}
	
	/** ceo 사진 업로드
	 * @param images
	 * @param ra
	 * @param campNo
	 * @param referer
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/ceoPicUpload")
	public String ceoPicUpload(@RequestParam(value = "images", required = false) List<MultipartFile> images,
			  				   RedirectAttributes ra,
			  				   int campNo,
			  				   @RequestHeader("referer") String referer
							   ) throws Exception {
		
		int result = service.insertCeoPic(campNo, images);
		
		if(result > 0) {
			
			ra.addFlashAttribute("message", "업로드 성공");
			
		} else {
			
			ra.addFlashAttribute("message", "업로드 실패");

		}
		
		
		return "redirect:" + referer;
	}
	
	@GetMapping("/ceoPicDelete")
	public int ceoPicDelete() {
		
		int result = 0;
		
	
		
		return result;
	}
	
}
