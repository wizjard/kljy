package com.juncsoft.KLJY.biomedical.member.action;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.juncsoft.KLJY.biomedical.entity.MemberInfo;

public class MemberLoginFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String path = request.getServletPath();
		if (path.startsWith("/module/biomedical/member/resources")
				|| path.startsWith("/module/biomedical/member/common")
				|| path.equalsIgnoreCase("/module/biomedical/member/login.jsp")
				|| path
						.equalsIgnoreCase("/module/biomedical/member.do?method=login")) {
			arg2.doFilter(request, response);
		} else {
			MemberInfo mem = (MemberInfo) request.getSession().getAttribute(
					"MemberInfo");
			if (mem == null) {
				response.sendRedirect(request.getContextPath()
						+ "/module/biomedical/member/common/login_timeout.jsp");
			} else {
				arg2.doFilter(request, response);
			}
		}
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
