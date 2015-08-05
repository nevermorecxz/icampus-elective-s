package com.irengine.campus.rest;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.irengine.campus.security.xauth.TokenProvider;
import com.irengine.campus.service.UserBaseInfoService;

@RestController
@RequestMapping("/users")
public class UserBaseInfoController {

	private static Logger logger = LoggerFactory
			.getLogger(UserBaseInfoController.class);

	@Autowired
	private UserBaseInfoService userBaseInfoService;

	@Inject
	private TokenProvider tokenProvider;

	@Inject
	private AuthenticationManager authenticationManager;

	@Inject
	private UserDetailsService userDetailsService;

	/**
	 * 获取token
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/token", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> getToken(
			@RequestParam("username") String username,
			@RequestParam("password") String password) {
		logger.debug("用户"+username+"正在调用获取token接口");
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				username, password);
		Authentication authentication = this.authenticationManager
				.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetails details = this.userDetailsService
				.loadUserByUsername(username);
		return new ResponseEntity<>(tokenProvider.createToken(details),
				HttpStatus.OK);
	}

}
