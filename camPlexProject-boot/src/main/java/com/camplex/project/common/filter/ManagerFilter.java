package com.camplex.project.common.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.camplex.project.member.model.dto.Member;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class ManagerFilter implements Filter{


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		HttpSession session = req.getSession();
		
		
		Member loginMember = (Member) session.getAttribute("loginMember");

		if(loginMember == null) {
			
			resp.sendRedirect("/loginError");
			
		}else {
			
			if( !loginMember.getMemberType().equals("M") ) {
				resp.sendRedirect("/managerError");
				
			} else {
				chain.doFilter(request, response);
			}
		}
		
		
				
	}}
