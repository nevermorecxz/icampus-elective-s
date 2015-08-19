package com.irengine.campus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irengine.campus.domain.StatisticalDataOfSelectCourseAssist;
import com.irengine.campus.repository.StatisticalDataOfSelectCourseAssistDao;

@Service
@Transactional
public class StatisticalDataOfSelectCourseAssistService {

	@Autowired
	private StatisticalDataOfSelectCourseAssistDao statisticalDataOfSelectCourseAssistDao;

	public List<StatisticalDataOfSelectCourseAssist> save(List<StatisticalDataOfSelectCourseAssist> statisticalDataOfSelectAssist) {
		return statisticalDataOfSelectCourseAssistDao.save(statisticalDataOfSelectAssist);
	}
}
