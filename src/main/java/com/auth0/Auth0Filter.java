package com.auth0;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Auth0Filter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String accessToken = (String) SessionUtils.get(req, "accessToken");
		String idToken = (String) SessionUtils.get(req, "idToken");
		if (accessToken == null && idToken == null) {
			res.sendRedirect("/login");
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
