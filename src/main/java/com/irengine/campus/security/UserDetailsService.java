package com.irengine.campus.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.irengine.campus.domain.UserBaseInfo;
import com.irengine.campus.repository.UserBaseInfoDao;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserDetailsService implements
		org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UserBaseInfoDao userBaseInfoDao;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String username) {
		UserBaseInfo user = userBaseInfoDao.findOneByUsername(username);
		if (user == null) {
			/* 用户不存在 */
			throw new UsernameNotFoundException("User " + username
					+ " was not found in the database");
		}
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		/* 将权限名放入grantedAuthorities集合中 */
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user
				.getAuthority().getName());
		grantedAuthorities.add(grantedAuthority);
		/* 返回有用户帐号,密码,权限的信息类 */
		return new org.springframework.security.core.userdetails.User(username,
				user.getPassword(), grantedAuthorities);
	}
}
