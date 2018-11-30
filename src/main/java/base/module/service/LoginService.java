package base.module.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import base.employee.mapper.EmployeeMapper;

@Service
public class LoginService implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
	
	@Autowired
	private EmployeeMapper employeeMapper;
	
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		logger.debug("loadUserByUsername username : " + username);
		Map<String, Object> result = null;
		try {
			result = employeeMapper.selectEmployeeByCode(username);
		} catch (Exception e) {
			logger.error(e.toString());
			throw new RuntimeException(e.getMessage());
		}
		
		if (result == null) {
			throw new UsernameNotFoundException("No user found with input ID : " + username);
		}
		
		Collection<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
		
		UserDetails user = new User(username, (String) result.get("Password"), roles);
		
		return user;
	}

}
