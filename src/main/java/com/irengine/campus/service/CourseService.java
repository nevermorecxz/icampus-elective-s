package com.irengine.campus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irengine.campus.domain.Course;
import com.irengine.campus.repository.CourseDao;

@Service
@Transactional
public class CourseService {

	@Autowired
	private CourseDao courseDao;

	public Course findOneById(Long courseId) {
		return courseDao.findOne(courseId);
	}

	public List<Course> findAll() {
		return courseDao.findAll(new Sort(Direction.ASC, "id"));
	}
}