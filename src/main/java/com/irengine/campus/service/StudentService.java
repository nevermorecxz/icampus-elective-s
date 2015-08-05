package com.irengine.campus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irengine.campus.domain.Student;
import com.irengine.campus.repository.StudentDao;

@Service
@Transactional
public class StudentService {

	@Autowired
	private StudentDao studentDao;
	
	public List<Student> findAll() {
		return studentDao.findAll(new Sort(Direction.DESC,"id"));
	}

	public Student findOneById(Long studentId) {
		return studentDao.findOne(studentId);
	}

	public Student findOneByUserBaseInfoId(Long id) {
		return studentDao.findOneByUserBaseInfoId(id);
	}

	public List<Student> findAllByNClassId(Long nClassId) {
		return studentDao.findAllByNClassId(nClassId);
	}

	public Student save(Student student) {
		return studentDao.save(student);
	}

	public List<Student> findAllByAttendance(Integer th) {
		return studentDao.findAllByAttendance(th);
	}
	
}
