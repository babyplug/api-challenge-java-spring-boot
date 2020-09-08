package com.babyplug.challenge.security.configuration.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class SecurityUser extends User implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	private Long organizationId;
	private String displayName;

	public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
//		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		super(username, password, authorities);

		this.userId = userId;
		this.displayName = displayName;
		this.organizationId = organizationId;
	}

	public SecurityUser(Long userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);

		this.userId = userId;
		this.displayName = displayName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	
	


}
