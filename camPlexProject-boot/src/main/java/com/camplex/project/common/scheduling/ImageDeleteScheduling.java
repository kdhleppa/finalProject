package com.camplex.project.common.scheduling;

import java.io.File;
import java.util.Arrays;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.camplex.project.board.model.service.BoardService;

import jakarta.servlet.ServletContext;

// 스프링이 일정 시간마다 해당 객체를 이용해서 코드를 수행
// == 스프링이 해당 클래스를 객체로 만들어서 관리를 해야함
// == Bean 등록

// @Controller, @Service, @Repository 의 부모 어노테이션
@Component  // Bean등록을 하겠다고 명시하는 어노테이션
public class ImageDeleteScheduling {
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private BoardService service;
	
	
//	@Scheduled(fixedDelay = 10000) // ms단위
//	// 일(1초) -> 대기(10초) -> 일(1초) -> 대기(10초)
	@Scheduled(fixedRate = 10000)
	// 일(1초)
	// 대기(10초)
	
	// cron="초 분 시 일 월 요일 [년도]" - 요일 : 1(SUN) ~ 7(SAT)
	@Scheduled(cron = "0,30 * * * * *") // 매 분 0초, 30초마다 수행
//	@Scheduled(cron = "0 0 * * * *")// 매 정시 (*시 0분 0초)
	public void test() {
//		System.out.println("스케줄러가 일정 시간마다 자동으로 출력");
		
		System.out.println("------------DB, 서버 불일치 파일 제거------------");
		
		// 서버에 저장된 파일 목록을 조회해서
		// DB에 저장된 파일 목록과 비교하여
		// 매칭되지 않는 서버 파일 제거
		
		// 1) 서버에 저장된 파일 목록 조회
		// -> application 객체를 이용해서
		// /resources/images/board의 실제 서버 경로를 얻어옴
		String filePath = servletContext.getRealPath("/images/boardImg/");
		
		// 2) filePath에 저장된 모든 파일 목록 읽어오기
		File path = new File(filePath);
		File[] imageArr = path.listFiles();
		
		// 배열 -> List로 변환
		List<File> serverImageList = Arrays.asList(imageArr);
		
		// 3) DB파일 목록 조회
		List<String> dbImageList = service.selectImageList();
		
		// 4) 서버에 파일 목록이 있을 경우에 비교를 시작
		if( !serverImageList.isEmpty() ) {
			
			// 5) 서버 파일 목록을 순차 접근
			for(File server : serverImageList) {
				
				// 6) 서버에 존재하는 파일이
				// DB(dbImageList)에 없다면 삭제
				
				// server.toString();
				
				// List.indexOf(객체) = 객체가 List에 있으면 해당 인덱스반환, 없으면 -1반환
				if( dbImageList.indexOf(server.getName()) == -1 ) {
					//db파일목록				서버 파일 이름 == -1 (없음)
					
					System.out.println(server.getName() + "삭제");
					server.delete(); // File.delete() : 파일 삭제
					
				}
				
			}
			
		}
		
	}


}


/*
 * @Scheduled
 * 
 * * Spring에서 제공하는 스케줄러 - 스케줄러 : 시간에 따른 특정 작업(Job)의 순서를 지정하는 방법.
 * 
 * 설정 방법 
 * 1) servlet-context.xml -> Namespaces 탭 -> task 체크 후 저장
 * 2) servlet-context.xml -> Source 탭 -> <task:annotation-driven/> 추가
 * 
 *
 * @Scheduled 속성
 *  - fixedDelay : 이전 작업이 끝난 시점으로 부터 고정된 시간(ms)을 설정.
 *    @Scheduled(fixedRate = 10000) // 이전 작업이 시작된 후 10초 뒤에 실행
 *    
 *  - fixedRate : 이전 작업이 수행되기 시작한 시점으로 부터 고정된 시간(ms)을 설정.
 *    @Scheduled(fixedDelay  = 10000) // 이전 작업이 끝난 후 10초 뒤에 실행
 *    
 *    
 * * cron 속성 : UNIX계열 잡 스케쥴러 표현식으로 작성 - cron="초 분 시 일 월 요일 [년도]" - 요일 : 1(SUN) ~ 7(SAT) 
 * ex) 2019년 9월 16일 월요일 10시 30분 20초 cron="20 30 10 16 9 2 " // 연도 생략 가능
 * 
 * - 특수문자 * : 모든 수. 
 * - : 두 수 사이의 값. ex) 10-15 -> 10이상 15이하 
 * , : 특정 값 지정. ex) 3,4,7 -> 3,4,7 지정 
 * / : 값의 증가. ex) 0/5 -> 0부터 시작하여 5마다 
 * ? : 특별한 값이 없음. (월, 요일만 해당) 
 * L : 마지막. (월, 요일만 해당)
 * @Scheduled(cron="0 * * * * *") // 매 분마다 실행
 * 
 * 
 * 
 * 
 * * 주의사항 - @Scheduled 어노테이션은 매개변수가 없는 메소드에만 적용 가능.
 * 
 */
