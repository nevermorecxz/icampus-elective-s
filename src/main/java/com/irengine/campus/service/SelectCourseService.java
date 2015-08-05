package com.irengine.campus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irengine.campus.domain.SelectCourse;
import com.irengine.campus.repository.SelectCourseDao;

@Service
@Transactional
public class SelectCourseService {

	@Autowired
	private SelectCourseDao selectCourseDao;

	public SelectCourse save(SelectCourse selectCourse) {
		return selectCourseDao.save(selectCourse);
	}

	public List<SelectCourse> findAllByPreferencesId(Long id) {
		return selectCourseDao.findAllByPreferencesId(id);
	}

	public void deleteAll(List<SelectCourse> selectCourses) {
		selectCourseDao.deleteInBatch(selectCourses);
	}

}
