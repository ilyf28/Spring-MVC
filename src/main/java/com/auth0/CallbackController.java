package com.auth0;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CallbackController {

	@Autowired
	private AuthController authController;
	private final String redirectOnFail;
	private final String redirectOnSuccess;
	
	public CallbackController() {
		this.redirectOnFail = "/login";
		this.redirectOnSuccess = "/portal/home";
	}
	
	@RequestMapping(value = "/callback", method = RequestMethod.GET)
	protected void getCallback(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		handle(request, response);
	}
	
	@RequestMapping(value = "/callback", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	protected void postCallback(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		handle(request, response);
	}
	
	private void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			Tokens tokens = authController.handle(request);
			SessionUtils.set(request, "accessToken", tokens.getAccessToken());
			SessionUtils.set(request, "idToken", tokens.getIdToken());
			response.sendRedirect(redirectOnSuccess);
		} catch (IdentityVerificationException e) {
			e.printStackTrace();
			response.sendRedirect(redirectOnFail);
		}
	}
}
