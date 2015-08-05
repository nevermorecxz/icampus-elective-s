package com.irengine.campus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.irengine.campus.domain.UserBaseInfo;

public interface UserBaseInfoDao extends JpaRepository<UserBaseInfo, Long>{

	@Query("select u from UserBaseInfo u where u.username=:username")
	UserBaseInfo findOneByUsername(@Param("username") String username);
	
}
