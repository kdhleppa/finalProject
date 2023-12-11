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
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	/** 캠핑장 정렬
	 * @param category
	 * @param model
	 * @return
	 */
	@GetMapping("/order")
	@ResponseBody
	public List<Camp> order(String category){
		List<Camp> list = new ArrayList<>();
	
		switch(category) {
		
		case "recommend": list = service.selectCampListReccomend(); break;
		case "current" : list = service.selectCampList(); break;
		case "popular" : list = service.selectCampListPopular(); break;
		case "old" : list = service.selectCampListOld(); break;
		
		}
		
		return list;
	}
	
	/** 캠핑장 분류
	 * @param category
	 * @param model
	 * @return
	 */
	@GetMapping("/category")
	@ResponseBody
	public List<Camp> category(String category){
		List<Camp> list = new ArrayList<>();
	
		switch(category) {
		
		case "all" : list = service.selectCampList(); break;
		default: list= service.selectCampListCategory(category); break;
		
		}
		
		return list;
	}
	
	/** 캠핑장 검색
	 * @param input
	 * @return
	 */
	@GetMapping("/searchCamp")
	@ResponseBody
	public List<Camp> searchCamp(String input){
		
		List<Camp> list = new ArrayList<>();
		
		list = service.searchCampList(input);
		
		return list;
		
	}
	
	/** 캠핑장 상세 조회
	 * @param campNo
	 * @param model
	 * @return
	 */
	@GetMapping("/{campNo}")
	public String campDetail(@PathVariable("campNo") int campNo,
							 @SessionAttribute(value="loginMember", required=false) Member loginMember,
							 Model model
							) {
		
		if(loginMember != null) {
			int memberNo = loginMember.getMemberNo();
			
			Map<String, Object> map = new HashMap<>();
			map.put("campNo", campNo);
			map.put("memberNo", memberNo);
			
			int checkStar = service.checkStar(map);
			
			if(checkStar > 0) {
				
				int starCount = service.selectStar(map);				
				model.addAttribute("starCount", starCount);
			}
		}
		
		Camp camp = service.selectCampOne(campNo);
				
		if(camp.getCampOption() != null) {
			
			String[] optionArr = camp.getCampOption().split("\\^\\^\\^");
			Map<String, Object> optionMap = new HashMap<>();
			
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
			
			model.addAttribute("option", optionMap);
			
		}
		
		if(camp.getCampAroundView() != null) {
			
			String[] arondViewArr = camp.getCampAroundView().split("\\^\\^\\^");
			Map<String, Object> aroundViewMap = new HashMap<>();
			
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
			
			model.addAttribute("aroundView", aroundViewMap);
			
		}
		
		model.addAttribute("camp", camp);
		
		return "camp/campingDetail";
	}
	
	/** 별점 처리
	 * @return
	 */
	@GetMapping("/star")
	@ResponseBody
	public int updateStar(int starNo,
						  int campNo,
						  @SessionAttribute("loginMember") Member loginMember
						) {
		int result = 0;
		int memberNo = loginMember.getMemberNo();
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("memberNo", memberNo);
		map.put("campNo", campNo);
		map.put("starNo", starNo);
		
		
		int checkStar = service.checkStar(map);
		
		if(checkStar > 0) {
			
			result = service.updateStar(map);
			
		} else {
			
			result = service.insertStar(map);
		}
		
		return result;
	}
	
	/** 예약 이동
	 * @param campDeNo
	 * @param model
	 * @return
	 */
	@GetMapping("/reservation")
	public String campReservation(int campDeNo,
								  String entDate,
								  String outDate,
								  int stayDay,
								  Model model,
								  @SessionAttribute(required=false) Member loginMember,
								  RedirectAttributes ra,
								  @RequestHeader("referer") String referer
			) {
		
		 String path = "";
		 Map<String, Object> map = new HashMap();
		 
		 map.put("entDate", entDate);
		 map.put("outDate", outDate);
		 map.put("stayDay", stayDay);
		 
		if(loginMember == null) {
			
			ra.addFlashAttribute("message", "로그인 후 이용해주세요.");
			path = "redirect:" + referer;
			
		} else {
			
			CampDetail campDetail = service.selectCampDetail(campDeNo);
			List<CampDetailImage> campDetailImages = service.selectCampDetailImageList(campDeNo);
			model.addAttribute("campDetail", campDetail);
			model.addAttribute("campDetailImages", campDetailImages);
			model.addAttribute("map", map);
			
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
	
	/** ceo 사진 지우기
	 * @param imgNo
	 * @param ra
	 * @return
	 */
	@GetMapping("/ceoPicDelete")
	@ResponseBody
	public int ceoPicDelete(int imgNo, RedirectAttributes ra) {
		
		int result = 0;
		
		result = service.ceoPicDelete(imgNo);
	
		if(result > 0) {
			ra.addFlashAttribute("message", "사진이 삭제되었습니다.");
		} else {
			ra.addFlashAttribute("message", "삭제 실패");
		}
		
		System.out.println(result);
		
		return result;
	}
	
	/** 날짜 선택시 캠핑장 비동기 보여주기 
	 * @param entDate
	 * @param outDate
	 * @param campNo
	 * @return
	 */
	@GetMapping("/showCampDe")
	@ResponseBody
	public List<CampDetail> showCampDe(String entDate, String outDate, int campNo){
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("entDate", entDate);
		map.put("outDate", outDate);
		map.put("campNo", campNo);
		
		List<CampDetail> list = service.selectCampDetailListNotRes(map);
		
		return list;
	}
	
	
}
