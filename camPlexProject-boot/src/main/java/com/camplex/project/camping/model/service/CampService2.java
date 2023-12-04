package com.camplex.project.camping.model.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.camplex.project.camping.model.dto.Camp;
import com.camplex.project.camping.model.dto.CampDetail;
import com.camplex.project.camping.model.dto.CampDetailImage;

public interface CampService2 {

	int campInsert(Camp camp, List<MultipartFile> images)
								throws IllegalStateException, IOException;

	int insertDeCamp(CampDetail campDetail, List<MultipartFile> campDeImges) throws IllegalStateException, IOException;
	

	List<CampDetail> selectDeCamp();

	List<CampDetailImage> selectDeCampImage();




	


	

}
