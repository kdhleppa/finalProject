package com.camplex.project.common.utility;

import java.text.SimpleDateFormat;

public class Util {

	public static String fileRename(String originFileName) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = sdf.format(new java.util.Date(System.currentTimeMillis()));

		int ranNum = (int) (Math.random() * 100000); // 5자리 랜덤 숫자 생성

		String str = "_" + String.format("%05d", ranNum);

		String ext = originFileName.substring(originFileName.lastIndexOf("."));

		return date + str + ext;
	}


	// Cross Site Scripting(XSS) : 
	// 웹 애플리케이션에서 발생하는 취약점
	// 권한이 없는 사용자가 악의적인 스크립트를 작성하는 것
	
	// 입력 값에 대한 검증이 이루어지지 않아서 발생
	// -> 모든 사용자에 대한 모든 입력값에 대해 필터링 해야함
	
	// 태그 기호를 엔티티 코드로 변환 
	// < 는 &lt;
	// > 는 &gt;
	// & 는 &amp
	// " 는 &quot;
	
	public static String XSSHandling(String content) {
		
		content = content.replaceAll("&", "&amp;");
		content = content.replaceAll("<", "&lt;");
		content = content.replaceAll(">", "&gt;");
		content = content.replaceAll("\"", "&quot;");
		
		
		return content;
	}
}
