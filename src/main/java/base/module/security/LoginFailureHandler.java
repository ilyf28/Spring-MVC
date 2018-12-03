package base.module.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private static final Logger logger = LoggerFactory.getLogger(LoginFailureHandler.class);
	private static final String REDIRECT_LOGIN_FAILURE = "spring-security-redirect-login-failure";
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		String accept = request.getHeader("accept");
		
		try {
			if (accept.indexOf("html") > -1) {
				String url = request.getParameter(REDIRECT_LOGIN_FAILURE);
				if (url != null) {
					logger.debug("Found redirect URL: " + url);
					getRedirectStrategy().sendRedirect(request, response, url);
				} else {
					super.onAuthenticationFailure(request, response, exception);
				}
			} else if (accept.indexOf("xml") > -1) {
				response.setContentType("application/xml");
				response.setCharacterEncoding("UTF-8");
				
				String data = "";
				data += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				data += "<response>";
				data += "<success>false</success>";
				data += "<message>로그인에 실패하였습니다. ";
				data += exception.getMessage() + "</message>";
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
				data += "\"success\": false,";
				data += "\"message\": \"로그인에 실패하였습니다. ";
				data += exception.getMessage() + "\"";
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
