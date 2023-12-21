package com.camplex.project.common.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.camplex.project.member.model.dto.Member;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class BoardFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		
		String[] arr = req.getRequestURI().split("/");
		
		try {
			String boardType = arr[2];
			
			List<Map<String, Object>> boardTypeList
				= (List<Map<String, Object>>)(req.getServletContext().getAttribute("boardTypeList"));
			
			for(Map <String,Object> boardTypeFl : boardTypeList) {
				
				if((boardTypeFl.get("BOARD_TYPE") + "").equals(boardType)) {
					req.setAttribute("boardTypeName", boardTypeFl.get("BOARD_TYPE_NAME"));
				}
			}
			
		} catch (Exception e) { }
		
		chain.doFilter(request, response);
		
		
	}

}
