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

public class EncodingFilter implements Filter {
	private String requestEncoding = "UTF-8";
	private String responseEncoding = "UTF-8";

	public void destroy() {

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		request.setCharacterEncoding(requestEncoding);
		response.setCharacterEncoding(responseEncoding);
		arg2.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("启动字符过滤器：");
		String requestEncoding0 = arg0.getInitParameter("request");
		String responseEncoding0 = arg0.getInitParameter("response");
		if (requestEncoding0 != null && requestEncoding0.length() > 0) {
			requestEncoding = requestEncoding0;
		}
		if (responseEncoding0 != null && responseEncoding0.length() > 0) {
			responseEncoding = responseEncoding0;
		}
		System.out.println("\t设定字符编码为->HttpServletRequest: " + requestEncoding
				+ " ,HttpServletResponse: " + responseEncoding);
	}

}
