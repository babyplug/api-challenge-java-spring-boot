package com.babyplug.challenge.security.filter;

import com.babyplug.challenge.core.exception.ErrorMessage;
import com.babyplug.challenge.core.utils.RequestUtil;
import com.babyplug.challenge.security.configuration.domain.AccountCredentials;
import com.babyplug.challenge.security.configuration.service.TokenAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	private TokenAuthenticationService tokenAuthenticationService;

	public JWTLoginFilter(String url, AuthenticationManager authManager,
			TokenAuthenticationService tokenAuthenticationService) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
		this.tokenAuthenticationService = tokenAuthenticationService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		AccountCredentials credentials = new ObjectMapper().readValue(request.getInputStream(), AccountCredentials.class);

		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(),
				credentials.getPassword()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		try {
			tokenAuthenticationService.addAuthentication(req, res, auth);
		} catch (Exception e) {
			ObjectMapper objectMapper = new ObjectMapper();
			HttpServletResponse response = (HttpServletResponse) res;

			ErrorMessage err = new ErrorMessage("E500", e.getMessage());
			response.setStatus(401);
			RequestUtil.sendJsonResponse(response, "access", objectMapper.writeValueAsString(err));
		}

	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		ObjectMapper objectMapper = new ObjectMapper();

		ErrorMessage err = new ErrorMessage("401", failed.getMessage());
		response.setStatus(401);
		RequestUtil.sendJsonResponse(response, "access", objectMapper.writeValueAsString(err));
	}

}
