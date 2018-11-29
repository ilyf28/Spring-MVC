package base.module.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
		ModelAndView model = new ModelAndView();
		
		if (error != null) {
			model.addObject("error", "Invalid ID and Password!");
		}
		if (logout != null) {
			model.addObject("message", "You've been logged out successfully.");
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		if (principal != null && principal instanceof User) {
			logger.info("Already logged in! " + ((User) principal).getUsername());
			model.setViewName("redirect:/");
		} else {
			logger.info("Go to the login page!");
			model.setViewName("login");
		}
		
		return model;
	}
	
	
}
