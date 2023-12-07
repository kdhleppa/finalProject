package com.camplex.project.camping.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.camplex.project.camping.model.dto.Camp;
import com.camplex.project.camping.model.dto.CampDetail;
import com.camplex.project.camping.model.dto.CampDetailImage;
import com.camplex.project.camping.model.service.CampService2;
import com.camplex.project.member.model.dto.Member;

import retrofit2.http.GET;

@Controller
@RequestMapping("/camp2")
public class CampController2 {

	@Autowired
	private CampService2 service;
	
	@GetMapping("/uploadCamp")
	public String uploadPage() {
		
		return "camp/campingDetailUpload";
	}
	
	@GetMapping("/deleteCampNoZ")
	@ResponseBody
	public int unloadCamp() {
		
		int delImgResult = service.delCampDeImgNumO();
		
		if(delImgResult > 0) {
			int delCampDeResult = service.delCampNoZ();
		}
		
		return delImgResult;
	}
	
	@PostMapping("/campInsert")
	public String campInsert(Camp camp
			, @RequestParam(value = "images", required = false) List<MultipartFile> images
			, RedirectAttributes ra
			, @RequestParam MultipartFile inputCampMap
			, @SessionAttribute("loginMember") Member loginMember
			, @RequestHeader("referer") String referer) throws IllegalStateException, IOException {
		
		String[] optionArr = camp.getCampOption().split(",");
		String[] aroundArr = camp.getCampAroundView().split(",");
		
		System.out.println(optionArr[0]);
		
		if(camp.getCampOption() == null) {
			camp.setCampOption(null);
		} else {
			String addr = String.join("^^^", optionArr);
			camp.setCampOption(addr);
		}
		
		
		if(camp.getCampAroundView() == null) {
			camp.setCampAroundView(null);
		} else {
			String addr = String.join("^^^", aroundArr);
			camp.setCampAroundView(addr);
		}
		camp.setMemberNo(loginMember.getMemberNo());
		
		
		int campNo = service.campInsert(camp, images, inputCampMap);
		
		int updateResult = service.updateCampDe(campNo);
		
		
		String message = null;
		String path = "redirect:";
		
		
		if(campNo > 0) {
			message = "캠핑장 등록 완료";
			path += "/camp/search";
		} else {
			message = "캠핑장 등록 실패";
			path += referer;
		}
		
		ra.addFlashAttribute("message", message);
		
		return path;
		
	}
	
	// 캠핑장 구역 insert + select

	@PostMapping("/insertDeCamp")
	@ResponseBody

	public List<CampDetail> insertSelectDeCamp(CampDetail campDetail
			, @RequestParam(value = "campDeImges", required = false) List<MultipartFile> campDeImges)
					throws IllegalStateException, IOException {
		
		int campDeNo = service.insertDeCamp(campDetail, campDeImges);
		
		// 캠프장 장소 정보 리스트
		List<CampDetail> campDetailList = service.selectDeCamp();
		
		
		return campDetailList;
		
	}
	
	@GetMapping("/deleteCampDe")
	@ResponseBody
	public int deleteCampDe(int campDeNo) {
		
		int result = service.deleteCampDeImg(campDeNo);
		
		result += service.deleteCampDe(campDeNo);
		
		return result;
		
	}
	
	/** 캠핑장 삭제
	 * @param campNo
	 * @return
	 */
	@GetMapping("/deleteCamp")
	public String deleteCamp(int campNo, 
							 RedirectAttributes ra,
							 @RequestHeader("referer") String referer
							) {
		
		String path = "redirect:";
		int result = service.deleteCamp(campNo);
		
		if(result>0) {
			
			path += "/camp/search";
			ra.addFlashAttribute("message", "삭제 성공");
			
		} else {
			
			path += referer;
			ra.addFlashAttribute("message", "삭제 실패");
			
		}
		
		return path;
	}
}
