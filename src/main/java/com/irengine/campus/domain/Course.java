package com.irengine.campus.domain;

/**
 * 课程
 * @author wujing
 * */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

import org.springframework.web.bind.annotation.RestController;

@RestController
@Table(name = "ele_courses")
public class Course extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -5489418411306238894L;

	@Column(nullable = false,unique = true,length = 100)
	private String courseName;
	//等级考学期数
	private Integer levelTerm;
	//会考学期数 
	private Integer unifiedTerm;
	//等级考每周课时 
	private Integer levelPeriod;
	//会考每周课时
	private Integer unifiedPeriod;
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Integer getLevelTerm() {
		return levelTerm;
	}
	public void setLevelTerm(Integer levelTerm) {
		this.levelTerm = levelTerm;
	}
	public Integer getUnifiedTerm() {
		return unifiedTerm;
	}
	public void setUnifiedTerm(Integer unifiedTerm) {
		this.unifiedTerm = unifiedTerm;
	}
	public Integer getLevelPeriod() {
		return levelPeriod;
	}
	public void setLevelPeriod(Integer levelPeriod) {
		this.levelPeriod = levelPeriod;
	}
	public Integer getUnifiedPeriod() {
		return unifiedPeriod;
	}
	public void setUnifiedPeriod(Integer unifiedPeriod) {
		this.unifiedPeriod = unifiedPeriod;
	}
	
}
