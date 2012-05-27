package com.juncsoft.KLJY.filter_listener;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.juncsoft.KLJY.user.entity.User;

public class TimeOutFilter implements Filter {
	private String[] noSessions;
	private String timeout_page = "";

	public void destroy() {

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String path = request.getServletPath();
		for (String pathStart : noSessions) {
			if (path.startsWith(pathStart)) {
				arg2.doFilter(request, response);
				return;
			}
		}
		User user = (User) request.getSession().getAttribute("user");
		if (request.getHeader("x-requested-with") != null
				&& request.getHeader("x-requested-with").equalsIgnoreCase(
						"XMLHttpRequest")) {
			if (user == null) {
				response.setHeader("sessionstatus", "timeout");
				return;
			} else{
				arg2.doFilter(request, response);
				return;
			}
		} else {
			if (user == null) {
				response.sendRedirect(request.getContextPath()+timeout_page);
				return;
			} else{
				arg2.doFilter(request, response);
				return;
			}
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("启动超时过滤器：");
		String param_value = arg0.getInitParameter("noSession");
		System.out.println("\t设置不过滤超时的路径->" + param_value);
		if (param_value != null && param_value.length() > 0) {
			noSessions = param_value.split(",");
		} else {
			noSessions = new String[] { "" };
		}
		timeout_page = arg0.getInitParameter("timeout_page");
	}

}
