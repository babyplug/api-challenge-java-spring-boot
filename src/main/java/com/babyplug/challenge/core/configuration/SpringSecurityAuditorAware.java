package com.babyplug.challenge.core.configuration;

import com.babyplug.challenge.security.configuration.service.AuthenticationFacade;
import com.babyplug.challenge.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<User> {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Override
    public Optional<User> getCurrentAuditor() {
        Optional<User> userOptional = null;
        if (authenticationFacade.getAuthentication() != null) {
            userOptional = Optional.ofNullable((User) authenticationFacade.getAuthentication());
        } else {
            userOptional = Optional.ofNullable(new User());
        }
        return userOptional;
    }

}
