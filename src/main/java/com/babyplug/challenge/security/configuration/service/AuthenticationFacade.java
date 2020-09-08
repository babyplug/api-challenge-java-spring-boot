package com.babyplug.challenge.security.configuration.service;

import com.babyplug.challenge.user.domain.User;

public interface AuthenticationFacade {
	User getAuthentication();
}
