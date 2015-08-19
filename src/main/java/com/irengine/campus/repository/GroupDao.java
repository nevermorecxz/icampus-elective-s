package com.irengine.campus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.irengine.campus.domain.Group;

public interface GroupDao extends JpaRepository<Group, Long>{

}
