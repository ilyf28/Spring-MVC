package common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import common.model.Authentication;
import common.util.AuthenticationUtil;
import common.util.WebUtil;

public class RequestInterceptor implements HandlerInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean result = false;
		
		try {
			if (excludeUrl(request)) {
				result = true;
			} else {
				Authentication authentication = AuthenticationUtil.getAuthentication(request);
				
				if (authentication.isAuthenticate()) {
					result = true;
				} else {
					try {
						WebUtil.alertAndForward("로그인 후 이용할 수 있습니다.", "/", response.getWriter());
						result = false;
					} catch (Exception e) {
						logger.error(e.getMessage());
						new Exception("문제가 발생했습니다.\n관리자에게 문의해주세요.");
						result = false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result = false;
		}
		
		return result;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}
	
	private boolean excludeUrl(HttpServletRequest request) {
		String uri = request.getRequestURI().toLowerCase();
		logger.debug("request URI : " + uri);
		if (uri.equalsIgnoreCase("/") || uri.indexOf("/login") > -1 || uri.indexOf("/logout") > -1) return true;
		else return false;
	}

}
