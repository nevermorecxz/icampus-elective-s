package com.irengine.campus.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irengine.campus.domain.Preferences;
import com.irengine.campus.repository.PreferencesDao;

@Service
@Transactional
public class PreferenceService {

	@Autowired
	private PreferencesDao preferencesDao;

	public Preferences save(Preferences preferences) {
		return preferencesDao.save(preferences);
	}

	public Preferences findOneById(Long id) {
		return preferencesDao.findOne(id);
	}

	public List<Preferences> findAll() {
		return preferencesDao.findAll(new Sort(Direction.DESC, "id"));
	}

	public Preferences findOneByCurrentTime(Date date) {
		return preferencesDao.findOneByCurrentTime(date).get(0);
	}

	public Preferences findOneByCurrentTimeAndTh(Date date, Integer attendance) {
		return preferencesDao.findOneByCurrentTimeAndTh(date, attendance);
	}

}
