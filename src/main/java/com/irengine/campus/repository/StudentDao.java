package com.irengine.campus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.irengine.campus.domain.Student;

public interface StudentDao extends JpaRepository<Student, Long>{

	@Query("select s from Student s where s.baseInfo.id=:id")
	Student findOneByUserBaseInfoId(@Param("id") Long id);

	@Query("select s from Student s where s.nClass.id=:nClassId")
	List<Student> findAllByNClassId(@Param("nClassId") Long nClassId);

	@Query("select s from Student s where s.nClass.attendance=:th")
	List<Student> findAllByAttendance(@Param("th") Integer th);

}
