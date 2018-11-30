package base.module.provider;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import base.module.service.LoginService;

@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginAuthenticationProvider.class);
	
	@Autowired
	private LoginService loginService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		
		User user;
		Collection<? extends GrantedAuthority> authorities;
		
		try {
			user = (User) loginService.loadUserByUsername(username);
			
			logger.debug("authentication username : " + username + " / password : " + password.substring(0, 10));
			logger.debug("user username : " + user.getUsername() + " / password : " + user.getPassword().substring(0, 10));
			
			if (!password.equals(user.getPassword())) throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
			
			authorities = user.getAuthorities();
		} catch (UsernameNotFoundException e) {
			logger.error(e.toString());
			throw new UsernameNotFoundException(e.getMessage());
		} catch (BadCredentialsException e) {
			logger.error(e.toString());
			throw new BadCredentialsException(e.getMessage());
		} catch (Exception e) {
			logger.error(e.toString());
			throw new RuntimeException(e.getMessage());
		}
		
		return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
