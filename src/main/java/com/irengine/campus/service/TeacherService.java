package com.irengine.campus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irengine.campus.domain.Teacher;
import com.irengine.campus.repository.TeacherDao;

@Service
@Transactional
public class TeacherService {

	@Autowired
	private TeacherDao teacherDao;

	public List<Teacher> findAllByThAndCourseId(Integer th, Long id) {
		return teacherDao.findByThAndCourseId(th, id);
	}
}
