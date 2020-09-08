package com.babyplug.challenge.security.configuration.domain;

public class SecurityConstants {

	public static final long EXPIRATION_TIME = 36000000 ; // milliseconds = 1 day
//	public static final long EXPIRATION_TIME = 300000 ; // milliseconds = 5 minute

	public static final String TOKEN_PREFIX = "Bearer";
	
	public static final String HEADER_STRING = "Authorization";
	
	public static final String SECRET = "SecretKeyToGenJWTs";

}
