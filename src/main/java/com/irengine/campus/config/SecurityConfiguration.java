package com.irengine.campus.config;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

import com.irengine.campus.security.Http401UnauthorizedEntryPoint;
import com.irengine.campus.security.xauth.TokenProvider;
import com.irengine.campus.security.xauth.XAuthTokenConfigurer;

/**
 * 认证配置
 * 
 * @author huang
 *
 */
@Configuration
@AutoConfigureAfter(XAuthConfiguration.class)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private Http401UnauthorizedEntryPoint authenticationEntryPoint;

	@Autowired
	private UserDetailsService userDetailsService;

	@Inject
	private TokenProvider tokenProvider;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(
				passwordEncoder());
	}

	/**
	 * 对接口进行权限管理
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint).and()
				.csrf().disable().headers().frameOptions().disable().and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				// .regexMatchers(HttpMethod.GET,"/students").hasAuthority(AuthoritiesConstants.ADMIN)
				// .regexMatchers(HttpMethod.POST,"/students/elective").hasAuthority(AuthoritiesConstants.STUDENT)
				.regexMatchers(HttpMethod.POST, "/users/token").permitAll()
				.and().apply(securityConfigurerAdapter());// token验证
	}

	private XAuthTokenConfigurer securityConfigurerAdapter() {
		return new XAuthTokenConfigurer(userDetailsService, tokenProvider);
	}

	@Bean
	public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
		return new SecurityEvaluationContextExtension();
	}
}
