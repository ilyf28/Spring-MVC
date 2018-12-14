package com.auth0;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PortalHomeController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/portal/home", method = RequestMethod.GET)
	protected String home(final HttpServletRequest request, final Model model) {
		logger.info("Home page");
		String accessToken = (String) SessionUtils.get(request, "accessToken");
		String idToken = (String) SessionUtils.get(request, "idToken");
		if (accessToken != null) {
			model.addAttribute("userId", accessToken);
		} else if (idToken != null) {
			model.addAttribute("userId", idToken);
		}
		return "portal/home";
	}
}
