package com.irengine.campus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irengine.campus.domain.StatisticalDataOfSelectCourse;
import com.irengine.campus.repository.StatisticalDataOfSelectCourseDao;

@Service
@Transactional
public class StatisticalDataOfSelectCourseService {

	@Autowired
	private StatisticalDataOfSelectCourseDao statisticalDataOfSelectCourseDao;

	public StatisticalDataOfSelectCourse save(StatisticalDataOfSelectCourse statisticalDataOfSelectCourse) {
		return statisticalDataOfSelectCourseDao.save(statisticalDataOfSelectCourse);
	}
}
