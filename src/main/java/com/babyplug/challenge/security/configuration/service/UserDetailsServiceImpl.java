package com.babyplug.challenge.security.configuration.service;

import com.babyplug.challenge.core.configuration.Messages;
import com.babyplug.challenge.core.exception.ClientBadRequestException;
import com.babyplug.challenge.security.configuration.domain.SecurityUser;
import com.babyplug.challenge.user.domain.User;
import com.babyplug.challenge.user.domain.UserRepository;
import com.babyplug.challenge.user.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service("userDetailsService")
public class UserDetailsServiceImpl extends AbstractUserDetailsAuthenticationProvider {

	private final Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserGrantedAuthority userGrantedAuthority;

	@Autowired
	private Messages msg;

	@Autowired
	private UserService userService;

	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
	        UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		if (authentication.getCredentials() == null) {
			logger.debug("Authentication failed: no credentials provided");

			throw new BadCredentialsException(
			        messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}

	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
	        throws AuthenticationException {
		try {
			UserDetails loadedUser = null;

			// You can get the password here
			String password = authentication.getCredentials().toString();
			List<User> userOp = userService.findByUsernameAndDeleted(username.trim(), false);

			if (CollectionUtils.isEmpty(userOp)) {
				logger.error("Username not found: '" + username + "'");
				ClientBadRequestException clientBad = new ClientBadRequestException("E001",
				        "Username or Password Invalid!");
				throw new UsernameNotFoundException("Username or Password Invalid!", clientBad);
			}

			User user = userOp.get(0);
			User loginResult = internalLogin(user, password);

			if (loginResult != null) {
//				loadedUser = new SecurityUser(username, password, userGrantedAuthority.getAuthorities(user));
				loadedUser = new SecurityUser(user.getId(), username, password, userGrantedAuthority.getAuthorities(user));
			}

			return loadedUser;
		} catch (UsernameNotFoundException | InternalAuthenticationServiceException ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
		}
	}

	private User internalLogin(User user, String password) {
		boolean isMatch = passwordEncoder().matches(password, user.getPassword());
		if (isMatch) {
			return user;
		}
		return null;
	}

	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}

}
