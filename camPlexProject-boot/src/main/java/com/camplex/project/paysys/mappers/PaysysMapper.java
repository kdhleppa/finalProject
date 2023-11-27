package com.camplex.project.paysys.mappers;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaysysMapper {

	// 조회후 카트가 없으면 생성
	void createCart(@Param("memberNo") int memberNo);

	int searchMembersCartNo(@Param("memberNo") int memberNo);

	int insertCart(Map<String, Object> map);

	int searchCartItem(Map<String, Object> map);

	int searchCartNo(@Param("memberNo") int memberNo);

}
