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

import base.employee.mapper.EmployeeMapper;

public class LoginService implements UserDetailsService {
	
	@Autowired
	private EmployeeMapper employeeMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("loadUserByUsername username : " + username);
		Map<String, Object> result = null;
		try {
			result = employeeMapper.selectEmployeeByCode(username);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		if (result == null) {
			throw new UsernameNotFoundException("No user found with input ID : " + username);
		}
		
		Collection<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
		
		UserDetails user = new User(username, (String) result.get("Password"), roles);
		
		return user;
	}

}
