package com.irengine.campus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.irengine.campus.domain.Course;

public interface CourseDao extends JpaRepository<Course, Long> {

}