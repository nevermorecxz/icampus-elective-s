package com.irengine.campus.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.irengine.campus.domain.Preferences;

public interface PreferencesDao extends JpaRepository<Preferences, Long> {

	@Query("select p from Preferences p where :date>=p.startDate and :date<=p.endDate")
	List<Preferences> findOneByCurrentTime(@Param("date") Date date);

	@Query("select p from Preferences p where :date>=p.startDate and :date<=p.endDate and p.th=:attendance")
	Preferences findOneByCurrentTimeAndTh(@Param("date") Date date, @Param("attendance") Integer attendance);

}