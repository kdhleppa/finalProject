package com.camplex.project.camping.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import com.camplex.project.camping.model.dto.Camp;
import com.camplex.project.camping.model.dto.CampSiteImage;

@Mapper
public interface CampMapper2 {

	/** 캠프장 삽입(캠프장 이름, 주소, 전화번호, 부대 시설(옵션), 주변 환경, 매너 타임 등)
	 * @param camp
	 * @param images
	 * @return
	 */
	int campInsert(Camp camp, List<MultipartFile> images);

	/** 캠프장 삽입(이미지)
	 * @param uploadList
	 * @return
	 */
	int insertImageList(List<CampSiteImage> uploadList);

}
