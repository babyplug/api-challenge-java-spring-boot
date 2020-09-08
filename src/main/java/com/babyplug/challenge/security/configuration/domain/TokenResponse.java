package com.babyplug.challenge.security.configuration.domain;

import java.io.Serializable;

public class TokenResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String prefix;
	private String token;

	public TokenResponse(String tokenPrefix, String token) {
		this.prefix = tokenPrefix;
		this.token = token;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
