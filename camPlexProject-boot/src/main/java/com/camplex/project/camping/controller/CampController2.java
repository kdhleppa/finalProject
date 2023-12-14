package com.camplex.project.camping.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	/** 캠핑장 삽입
	 * @param camp
	 * @param images
	 * @param ra
	 * @param inputCampMap
	 * @param referer
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@PostMapping("/campInsert")
	public String campInsert(Camp camp
			, @RequestParam(value = "images", required = false) List<MultipartFile> images
			, RedirectAttributes ra
			, @RequestParam MultipartFile inputCampMap
			, @RequestHeader("referer") String referer) throws IllegalStateException, IOException {
		
		if(camp.getCampOption() != null) {
			String[] optionArr = camp.getCampOption().split(",");
			String addr = String.join("^^^", optionArr);
			camp.setCampOption(addr);
		} else {
			camp.setCampOption(null);
		}
		
		if(camp.getCampAroundView() != null) {
			String[] aroundArr = camp.getCampAroundView().split(",");
			String addr = String.join("^^^", aroundArr);
			camp.setCampAroundView(addr);
		} else {
			camp.setCampAroundView(null);
		}
		

		int ceoNum = camp.getMemberNo();

		
		Member checkCEO = service.checkCEO(ceoNum);
		
		String message = null;
		String path = "redirect:";
		
		if(checkCEO == null) {
			message = "CEO 넘버가 잘못되었습니다.";
			path += referer;
			return path;
		}
		
		int campNo = service.campInsert(camp, images, inputCampMap);
		
		
		int updateResult = service.updateCampDe(campNo);
		
		
		
		if(campNo > 0) {
			message = "캠핑장 등록 완료";
			path += "/camp/search";
		} else if(campNo == 100){
			message = "CEO 넘버가 잘못되었습니다.";
			path += referer;
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
	
	@PostMapping("/updateDeCamp")
	@ResponseBody
	public List<CampDetail> updateSelcetDeCamp(CampDetail campDetail
			, @RequestParam(value = "campDeImges", required = false) List<MultipartFile> campDeImges)
					throws IllegalStateException, IOException {
		
		int result = service.upInsertDeCamp(campDetail, campDeImges);
		
		int campNo = campDetail.getCampNo();
		
		List<CampDetail> campDetailList = service.selectDeCampOfCampDeNo(campNo);
		
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
	
	/** 캠핑장 수정 조회
	 * @param campNo
	 * @param model
	 * @return
	 */
	@GetMapping("/editCampForward")
	public String editCampForward(int campNo, Model model) {
		
		Camp campList = service.searchCampForCampNo(campNo);
		
		List<CampDetail> campDeList = service.searchCampDeForCampNo(campNo);
		
		model.addAttribute("campList", campList);
		model.addAttribute("campDeList", campDeList);
		
		return "camp/campingEdit";
	}
	
	
	/** 캠핑장 수정
	 * @param camp
	 * @param images
	 * @param ra
	 * @param inputCampMap
	 * @param loginMember
	 * @param referer
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@PostMapping("/editCamp")
	public String editCamp(
			Camp camp
			 ,@RequestParam(value = "images", required = false) List<MultipartFile> images
			, RedirectAttributes ra
			, @RequestParam MultipartFile inputCampMap
			, @RequestHeader("referer") String referer) throws IllegalStateException, IOException {
		
		if(camp.getCampOption() != null) {
			String[] optionArr = camp.getCampOption().split(",");
			String addr = String.join("^^^", optionArr);
			camp.setCampOption(addr);
		}
		
		if(camp.getCampAroundView() != null) {
			String[] aroundArr = camp.getCampAroundView().split(",");
			String addr = String.join("^^^", aroundArr);
			camp.setCampAroundView(addr);
			
		}
		
		
		int campUpdateresult = service.campUpdate(camp, images, inputCampMap);
		
		int campNo = camp.getCampNo();
		
		int campDeUpdateResult = service.updateCampDe(campNo);
		
		
		String message = null;
		String path = "redirect:";
		
		
		if(campUpdateresult > 0) {
			message = "캠핑장 업데이트 완료";
			path += "/camp/search";
		} else {
			message = "캠핑장 업데이트 실패";
			path += referer;
		}
		
		ra.addFlashAttribute("message", message);
		
		
		return path;
		
		
	}
	
	
	/** 맵에 사용될 캠핑장 select (ajax)
	 * @return
	 */
	@GetMapping("/selectForMapOfCamp")
	@ResponseBody
	public List<Camp> selectForMapOfCamp() {
		
		List<Camp> campList = service.selectForMapOfCamp();
		
		return campList;
		
	}
	
	
	/** 카테고리에 따른 캠프 select
	 * @param category
	 * @return
	 */
	@GetMapping("/selectForMapOfCampCategory")
	@ResponseBody
	public List<Camp> selectForMapOfCampCategory(String category) {
		
		List<Camp> campList = service.selectForMapOfCampCategory(category);
		
		return campList;
	}
	 
	
	
	
}
