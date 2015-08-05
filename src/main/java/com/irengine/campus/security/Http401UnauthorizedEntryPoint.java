package com.irengine.campus.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Returns a 401 error code (Unauthorized) to the client.
 */
/**
 * 非法请求处理
 * 
 * @author Administrator
 *
 */
@Component
public class Http401UnauthorizedEntryPoint implements AuthenticationEntryPoint {

	/**
	 * 身份验证,无权限调接口返回401 "Access Denied"?
	 */
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException arg2)
			throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
	}
}
