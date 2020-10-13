package com.babyplug.challenge.security.configuration;

import com.babyplug.challenge.security.configuration.service.TokenAuthenticationService;
import com.babyplug.challenge.security.configuration.service.UserDetailsServiceImpl;
import com.babyplug.challenge.security.filter.JWTAuthenticationFilter;
import com.babyplug.challenge.security.filter.JWTLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String LOGIN_PATH = "/login";
	private static final String LOGOUT_PATH = "/logout";
	private static final String USERNAME_PARAM = "username";
	private static final String PASSWORD_PARAM = "password";
	
//	@Autowired
//	private UserDetailsService userDetailsService;

	@Autowired
	private RestAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(userDetailsService);
//		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**").antMatchers(HttpMethod.OPTIONS, LOGIN_PATH);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    
	    final String[] AUTH_WHITELIST = {
                // -- swagger ui
				"/album/**", "/author/**", "/photo/**", "/photo-metadata/**",
                "/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
                "/configuration/security", "/swagger-ui.html", "/webjars/**"
	    };
        http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll();
        http.authorizeRequests().antMatchers("/images/**").permitAll();
        http.authorizeRequests().antMatchers("/public/**").permitAll();
        http.authorizeRequests().antMatchers("/resources/**").permitAll();
        
		http.csrf().disable().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.formLogin()
				.loginProcessingUrl(LOGIN_PATH).permitAll().usernameParameter(USERNAME_PARAM)
				.passwordParameter(PASSWORD_PARAM)
				.and()
				.logout().permitAll()
				.logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_PATH)).and()
				.addFilterBefore(new JWTLoginFilter(LOGIN_PATH, authenticationManager(), tokenAuthenticationService),
						UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new JWTAuthenticationFilter(tokenAuthenticationService),
						UsernamePasswordAuthenticationFilter.class);
		
		http.authorizeRequests().antMatchers(LOGIN_PATH).permitAll().anyRequest().authenticated();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return super.userDetailsServiceBean();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

}
