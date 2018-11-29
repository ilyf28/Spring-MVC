package common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import common.model.Authentication;

public class AuthenticationUtil {

	private static final String AUTHENTICATION = "AUTHENTICATION";
	
	public static Authentication getAuthentication(HttpServletRequest request) {
		Authentication authentication = (Authentication) request.getAttribute(AUTHENTICATION);
		
		if (authentication == null) {
			HttpSession session = request.getSession();
			
			if (session != null) {
				authentication = (Authentication) session.getAttribute(AUTHENTICATION);
			}
			
			if (authentication == null) {
				authentication = new Authentication();
			}
			
			request.setAttribute(AUTHENTICATION, authentication);
		}
		
		return authentication;
	}
	
	public static void setAuthentication(HttpServletRequest request, Authentication authentication) {
		HttpSession session = request.getSession();
		session.setAttribute(AUTHENTICATION, authentication);
		request.setAttribute(AUTHENTICATION, authentication);
	}
	
	public static void removeAuthentication(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(AUTHENTICATION);
		request.removeAttribute(AUTHENTICATION);
	}
}
