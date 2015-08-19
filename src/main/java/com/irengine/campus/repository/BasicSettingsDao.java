package com.irengine.campus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.irengine.campus.domain.BasicSettings;

public interface BasicSettingsDao extends JpaRepository<BasicSettings, Long>{
	
	@Query("select b from BasicSettings b where b.th=:th")
	BasicSettings findOneByTh(@Param("th")Integer th);

}
