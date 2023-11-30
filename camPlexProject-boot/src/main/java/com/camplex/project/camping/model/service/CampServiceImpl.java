package com.camplex.project.camping.model.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.camplex.project.camping.mappers.CampMapper;
import com.camplex.project.camping.model.dto.Camp;
import com.camplex.project.camping.model.dto.CampCeoImage;
import com.camplex.project.camping.model.dto.CampDetail;
import com.camplex.project.camping.model.dto.CampDetailImage;
import com.camplex.project.common.utility.Util;

@Service
public class CampServiceImpl implements CampService {

	@Autowired
	private CampMapper mapper;

	@Value("${camplex.camping.webpath}")
	private String webPath;

	@Value("${camplex.camping.location}")
	private String filePath;


	/** 캠프장 목록 조회
	 *
	 */
	@Override
	public List<Camp> selectCampList() {

		return mapper.selectCampList();
	}

	/** 캠프장 상세 조회
	 *
	 */
	@Override
	public Camp selectCampOne(int campNo) {

		return mapper.selectCampOne(campNo);
	}

	/** 캠핑장 예약 이동
	 *
	 */
	@Override
	public CampDetail selectCampDetail(int campDeNo) {

		return mapper.selectCampDetail(campDeNo);
	}

	/** 캠핑장 예약 이동 시 이미지 불러오기
	 *
	 */
	@Override
	public List<CampDetailImage> selectCampDetailImageList(int campDeNo) {

		return mapper.selectCampDetailImageList(campDeNo);
	}

	/** 위시리스트 중복 검사
	 *
	 */
	@Override
	public int checkDupCampWish(Map<String, Object> map) {

		return mapper.checkDupCampWish(map);
	}

	/** 위시리스트에 캠핑장 추가
	 *
	 */
	@Override
	public int insertWishlist(Map<String, Object> map) {

		return mapper.insertWishlist(map);
	}

	/** ceo 사진 업로드
	 * 
	 */
	@Override
	public int insertCeoPic(int campNo, List<MultipartFile> images) throws Exception{

		List<CampCeoImage> uploadList = new ArrayList<CampCeoImage>();

		for(int i = 0 ; i < images.size() ; i++ ) {

			if(images.get(i).getSize() > 0 ) {

				CampCeoImage img = new CampCeoImage();
				img.setCampCeoImagePath(webPath);
				img.setCampNo(campNo);
				
				String fileName = images.get(i).getOriginalFilename();
				img.setCampCeoImageOriginal(fileName);
				img.setCampCeoImageReName(Util.fileRename(fileName) );


				uploadList.add(img);
			}

		}
		
		
		int result = 0;

		if(!uploadList.isEmpty()) {

			result = mapper.insertImages(uploadList);

			if(uploadList.size() == result) {

				for(int i = 0 ; i < uploadList.size() ; i++) {

					String rename = uploadList.get(i).getCampCeoImageReName();

					images.get(i).transferTo( new File(filePath + rename));
					
				}

			}

		}

		return result;
	}

	/** ceo 사진 지우기
	 *
	 */
	@Override
	public int ceoPicDelete(int imgNo) {

		return mapper.ceoPicDelete(imgNo);
	}

	/** 예약 안된 캠핑장 호실 조회
	 *
	 */
	@Override
	public List<CampDetail> selectCampDetailListNotRes(Map<String, Object> map) {

		return mapper.selectCampDetailListNotRes(map);
	}

	/** 별점 개수 조회
	 *
	 */
	@Override
	public int selectStar(Map<String, Object> map) {
		return mapper.selectStar(map);
	}

	/** 별점 눌렀는지 확인
	 *
	 */
	@Override
	public int checkStar(Map<String, Object> map) {
		return mapper.checkStar(map);
	}

	/** 별점 업데이트
	 *
	 */
	@Override
	public int updateStar(Map<String, Object> map) {
		return mapper.updateStar(map);
	}

	/** 별점 추가
	 *
	 */
	@Override
	public int insertStar(Map<String, Object> map) {
		return  mapper.insertStar(map);
	}



}
