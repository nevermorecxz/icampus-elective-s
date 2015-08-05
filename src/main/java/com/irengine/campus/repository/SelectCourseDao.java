package com.irengine.campus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.irengine.campus.domain.SelectCourse;

public interface SelectCourseDao extends JpaRepository<SelectCourse, Long>{

	@Query("select s from SelectCourse s where s.preferences.id=:id")
	List<SelectCourse> findAllByPreferencesId(@Param("id") Long id);
	
}
