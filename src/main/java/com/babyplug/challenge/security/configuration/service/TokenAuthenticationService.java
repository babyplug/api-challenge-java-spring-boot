package com.babyplug.challenge.security.configuration.service;

import com.babyplug.challenge.core.configuration.Messages;
import com.babyplug.challenge.core.utils.RequestUtil;
import com.babyplug.challenge.security.configuration.domain.SecurityConstants;
import com.babyplug.challenge.security.configuration.domain.SecurityUser;
import com.babyplug.challenge.security.configuration.domain.TokenResponse;
import com.babyplug.challenge.user.domain.User;
import com.babyplug.challenge.user.service.UserService;
import com.babyplug.challenge.utils.UserUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service
public class TokenAuthenticationService {

	@Autowired
	private ObjectMapper objectMapper;

	private static final Logger logger = LogManager.getLogger(TokenAuthenticationService.class);

	@Autowired
	private UserService userService;

	@Autowired
	private EntityManager em;

	@Autowired
	private UserGrantedAuthority userGrantedAuthority;

	@Autowired
	private Messages msg;

	public void addAuthentication(HttpServletRequest req, HttpServletResponse res, Authentication auth)
	        throws Exception {
		SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
		// logger.info("add authentication");

		Date expiredDate = new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME);
		String jwt = tokenGenerator(securityUser, expiredDate);
//		String jwt = tokenGenerator(securityUser, null);

		TokenResponse token = new TokenResponse(SecurityConstants.TOKEN_PREFIX, jwt);

		RequestUtil.sendJsonResponse(res, "access", objectMapper.writeValueAsString(token));
	}

	private String tokenGenerator(SecurityUser user, Date expiredDate) throws JsonProcessingException {

		Map<String, Object> claims = new HashMap<>();

		claims.put("username", user.getUsername());
		claims.put("uid", user.getUserId());

		String jwt = Jwts.builder().setSubject("me").setClaims(claims).setExpiration(expiredDate)
		        .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET).compact();

		return jwt;
	}

	public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response)
	        throws JsonParseException, JsonMappingException, IOException {

		String token = request.getHeader(SecurityConstants.HEADER_STRING);
		if (token == null) {
			return null;
		}

		token = token.replace(SecurityConstants.TOKEN_PREFIX, "");

		if (!token.isEmpty()) {
			return authenticationFromToken(token, request);
		}
		return null;
	}

	private Authentication authenticationFromToken(String token, HttpServletRequest request) {
		Claims claims;

		try {
			claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody();

			// logger.info("authenticationFromToken -> claims: " + claims.toString());

			if (claims.containsKey("username")) {

				String username = claims.get("username").toString();

				List<User> userOp = userService.findByUsernameAndDeleted(username.trim(), false);

				if (CollectionUtils.isEmpty(userOp)) {
					throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Not found User");
				}

				User user = userOp.get(0);
				em.detach(user);

				UserUtils.validateUserStatus(user);

				// if token is not eq current jwt now check time of token that received must be > than old_time_jwt
//				if ( !user.getJwt().trim().equalsIgnoreCase(token.trim()) && claims.containsKey("time") ){
//					Long oldTimeJWT = Long.valueOf(user.getOldTimeJwt());
//					Long claimTime = Long.valueOf(claims.get("time").toString());
//
//					// if claimsTime lessThanOrEqual to 0 it's mean token received is <= older jwt
//					if (claimTime.compareTo(oldTimeJWT) <= 0) {
//						// logger.info("received token older than current");
//						throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Token expired.");
//					}
//				}


				user.setPassword(null);

				Collection<? extends GrantedAuthority> grantAuth = userGrantedAuthority.getAuthorities(user);
				return new UsernamePasswordAuthenticationToken(user, null, grantAuth);
			}

		} catch (ExpiredJwtException e) {
			logger.error(e);
			throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Token expired.");
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	public Long getUserIdFromToken(String token) {
		Long userId = null;
		token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
		if ("null".equals(token.trim())) {
			return null;
		}
		try {
			Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody();
			if (claims != null && claims.containsKey("uid")) {
				userId = Long.valueOf(claims.get("uid").toString());
			}
		} catch (ExpiredJwtException e) {

		}

		return userId;
	}

	public String getTimeFromToken(String token) {
		String res = null;
		if (token != null) {
			Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody();

			if (claims.containsKey("time")) {
				res = claims.get("time").toString();
			}
		}
		return res;
	}

}
