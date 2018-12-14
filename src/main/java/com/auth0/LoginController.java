package com.auth0;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	
	@Autowired
	private AuthController authController;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	protected String login(final HttpServletRequest request) {
		logger.debug("Performing login");
		String redirectUri = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/callback";
		String authorizeUrl = authController.buildAuthorizeUrl(request, redirectUri);
		return "redirect:" + authorizeUrl;
	}
}
