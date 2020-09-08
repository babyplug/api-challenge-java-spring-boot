package com.babyplug.challenge.security.configuration.service;

import com.babyplug.challenge.user.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.babyplug.challenge.user.domain.EUserType.*;

@Component
public class UserGrantedAuthority {

    public Collection<? extends GrantedAuthority> getAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        authorities.add(new SimpleGrantedAuthority(USER.name()));
//        if (user.getUserType() == null) {
//            authorities.add(new SimpleGrantedAuthority(USER.name()));
//        } else {
//            authorities.add(new SimpleGrantedAuthority(user.getUserType().name()));
//        }

        return authorities;
    }
	
}
