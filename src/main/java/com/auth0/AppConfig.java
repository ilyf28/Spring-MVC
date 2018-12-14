package com.auth0;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class AppConfig {

	@Value(value = "#{auth0['domain']}")
	private String domain;
	
	@Value(value = "#{auth0['clientId']}")
	private String clientId;
	
	@Value(value = "#{auth0['clientSecret']}")
	private String clientSecret;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean filterRegistration() {
		final FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new Auth0Filter());
		registration.addUrlPatterns("/portal/*");
		registration.setName(Auth0Filter.class.getSimpleName());
		return registration;
	}

	public String getDomain() {
		return domain;
	}

	public String getClientId() {
		return clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}
}
