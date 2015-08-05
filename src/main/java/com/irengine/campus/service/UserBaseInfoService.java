package com.irengine.campus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irengine.campus.domain.UserBaseInfo;
import com.irengine.campus.repository.UserBaseInfoDao;

@Service
@Transactional
public class UserBaseInfoService {

	@Autowired
	private UserBaseInfoDao userBaseInfoDao;

	public UserBaseInfo findOneByUsername(String currentUsername) {
		return userBaseInfoDao.findOneByUsername(currentUsername);
	}
	
}
