package com.camplex.project.common.config;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.camplex.project.common.filter.BoardFilter;
import com.camplex.project.common.filter.CeoFilter;
import com.camplex.project.common.filter.LoginFilter;
import com.camplex.project.common.filter.ManagerFilter;

@Configuration
public class FilterConfig {
	
	@Bean
	public FilterRegistrationBean<BoardFilter> boardFilter(){
		
		FilterRegistrationBean<BoardFilter> resiRegistrationBean = new FilterRegistrationBean<BoardFilter>();
		
		resiRegistrationBean.setFilter(new BoardFilter());
		
		String[] url = {"/board/*", "/board2/*"};
		resiRegistrationBean.setUrlPatterns(Arrays.asList(url)); // url 패턴 여러 개 지정
		resiRegistrationBean.setName("boardFilter"); // 이름
		resiRegistrationBean.setOrder(2); // 여러 필터가 있을 때 순서
		return resiRegistrationBean;
	}
	
	
	@Bean
	public FilterRegistrationBean<LoginFilter> loginFilter(){
		
		FilterRegistrationBean<LoginFilter> resiRegistrationBean = new FilterRegistrationBean<LoginFilter>();
		
		resiRegistrationBean.setFilter(new LoginFilter());
		
		String[] url = {"/member/logout", "/member/myPage", "/member/CEOMyPage",
						"/member/infoModify", "/member/updateMember",
						"/member/memberWithdrawal1", "/member/deleteMember", "/member/levelUpForm",
						"/member/levelUpFormCheck", "/member/phoneChange", "/member/reservationDetails",
						"/member/payHistory", "/member/campUpdate", "/member/reservationNManage",
						"/member/wishlist/*", "/member/selectQna", "/member/selectCeoQna",
						"/member/selectCampWish", "/member/selectItemWish", "/member/deleteCampWish/*",
						"/member/deleteItemWish/*",
						"/paysys/*"};
		resiRegistrationBean.setUrlPatterns(Arrays.asList(url)); // url 패턴 여러 개 지정
		resiRegistrationBean.setName("loginFilter"); // 이름
		resiRegistrationBean.setOrder(1); // 여러 필터가 있을 때 순서
		return resiRegistrationBean;
	}
	
	@Bean
	public FilterRegistrationBean<ManagerFilter> ManagerFilter(){
		
		FilterRegistrationBean<ManagerFilter> resiRegistrationBean = new FilterRegistrationBean<ManagerFilter>();
		
		resiRegistrationBean.setFilter(new ManagerFilter());
		
		String[] url = {"/member/managerMyPage"};
		resiRegistrationBean.setUrlPatterns(Arrays.asList(url)); // url 패턴 여러 개 지정
		resiRegistrationBean.setName("managerFilter"); // 이름
		resiRegistrationBean.setOrder(3); // 여러 필터가 있을 때 순서
		return resiRegistrationBean;
	}
	
	@Bean
	public FilterRegistrationBean<CeoFilter> CeoFilter(){
		
		FilterRegistrationBean<CeoFilter> resiRegistrationBean = new FilterRegistrationBean<CeoFilter>();
		
		resiRegistrationBean.setFilter(new CeoFilter());
		
		String[] url = {"/member/CEOMyPage"};
		resiRegistrationBean.setUrlPatterns(Arrays.asList(url)); // url 패턴 여러 개 지정
		resiRegistrationBean.setName("CeoFilter"); // 이름
		resiRegistrationBean.setOrder(4); // 여러 필터가 있을 때 순서
		return resiRegistrationBean;
	}

}
