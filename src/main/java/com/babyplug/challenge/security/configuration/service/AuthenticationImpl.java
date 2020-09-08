package com.babyplug.challenge.security.configuration.service;

import com.babyplug.challenge.user.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationImpl implements AuthenticationFacade {

	@Override
	public User getAuthentication() {
	    User user = null;
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
	        user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
	    
		return user;
	}

}
