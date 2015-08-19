package com.irengine.campus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irengine.campus.domain.BasicSettings;
import com.irengine.campus.repository.BasicSettingsDao;

@Service
@Transactional
public class BasicSettingsService {

	@Autowired
	private BasicSettingsDao basicSettingsDao;
	
	public BasicSettings findOneByTh(Integer th){
		return basicSettingsDao.findOneByTh(th);
	}
}
