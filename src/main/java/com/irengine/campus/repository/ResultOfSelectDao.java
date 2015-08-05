package com.irengine.campus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.irengine.campus.domain.ResultOfSelect;

public interface ResultOfSelectDao extends JpaRepository<ResultOfSelect, Long> {

	@Query("select r from ResultOfSelect r where r.preferences.id=:id and r.student.id=:id2")
	ResultOfSelect findOneByPreferencesIdAndStudentId(@Param("id") Long id,
			@Param("id2") Long id2);

	@Query("select r from ResultOfSelect r where r.preferences.id=:pId and r.student.nClass.id=:cId")
	List<ResultOfSelect> findAllByPreferencesIdAndnClassId(
			@Param("pId") Long preferencesId, @Param("cId") Long nClassId);

	@Query("select r from ResultOfSelect r where r.preferences.id=:pId")
	List<ResultOfSelect> findAllByPreferencesId(@Param("pId") Long preferencesId);

}
