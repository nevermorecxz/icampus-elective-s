package com.irengine.campus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irengine.campus.domain.ArrangeCourse;
import com.irengine.campus.repository.ArrangeCourseDao;

@Service
@Transactional
public class ArrangeCourseService {

	@Autowired
	private ArrangeCourseDao arrangeCourseDao;
	
	public ArrangeCourse save(ArrangeCourse arrayCourse){
		return arrangeCourseDao.save(arrayCourse);
		
	}
}
