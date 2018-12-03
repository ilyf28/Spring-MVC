package base.module.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private static final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
			throws IOException, ServletException {
		String accept = request.getHeader("accept");
		
		try {
			if (accept.indexOf("html") > -1) {
				super.onAuthenticationSuccess(request, response, authentication);
			} else if (accept.indexOf("xml") > -1) {
				response.setContentType("application/xml");
				response.setCharacterEncoding("UTF-8");
				
				String data = "";
				data += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				data += "<response>";
				data += "<success>true</success>";
				data += "<message>로그인성공</message>";
				data += "<url>" + super.getDefaultTargetUrl() + "</url>";
				data += "</response>";
				
				PrintWriter out = response.getWriter();
				out.println(data);
				out.flush();
				out.close();
			} else if (accept.indexOf("json") > -1) {
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				
				String data = "";
				data += "{\"response\":{";
				data += "\"success\": true,";
				data += "\"message\": \"로그인성공\",";
				data += "\"url\": \"" + super.getDefaultTargetUrl() + "\"";
				data += "}}";
				
				PrintWriter out = response.getWriter();
				out.println(data);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			logger.error(e.toString());
			throw new RuntimeException(e.getMessage());
		}
	}
}
