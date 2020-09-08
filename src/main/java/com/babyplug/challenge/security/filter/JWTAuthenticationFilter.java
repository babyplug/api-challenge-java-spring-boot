package com.babyplug.challenge.security.filter;

import com.babyplug.challenge.core.exception.ErrorMessage;
import com.babyplug.challenge.core.utils.RequestUtil;
import com.babyplug.challenge.security.configuration.service.TokenAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends GenericFilterBean {
	
	private static final Logger logger = LogManager.getLogger(JWTAuthenticationFilter.class);
	
	private TokenAuthenticationService tokenAuthenticationService;
	
	public JWTAuthenticationFilter(TokenAuthenticationService tokenAuthenticationService) {
		this.tokenAuthenticationService = tokenAuthenticationService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		// logger.info("filter token here");

		try {
			Authentication authentication = tokenAuthenticationService.getAuthentication(httpServletRequest,
					(HttpServletResponse) response);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			chain.doFilter(request, response);
		} catch (HttpClientErrorException e) {
			ObjectMapper objectMapper = new ObjectMapper();
			HttpServletResponse res = (HttpServletResponse) response;
			ErrorMessage err = new ErrorMessage("401", e.getMessage());
			res.setStatus(401);
			RequestUtil.sendJsonResponse(res, "access", objectMapper.writeValueAsString(err));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
	}

	

}
