package com.irengine.campus.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 每一门课的选课结果统计
 * 
 * @author wujing
 */
@Entity
@Table(name = "ele_statistical_assist")
public class StatisticalDataOfSelectCourseAssist extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 6660302340185460111L;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	private Integer levelNum;

	private Integer unifiedNum;
	
	public StatisticalDataOfSelectCourseAssist() {
		super();
	}

	public StatisticalDataOfSelectCourseAssist(Course course, Integer levelNum, Integer unifiedNum) {
		super();
		this.course = course;
		this.levelNum = levelNum;
		this.unifiedNum = unifiedNum;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Integer getLevelNum() {
		return levelNum;
	}

	public void setLevelNum(Integer levelNum) {
		this.levelNum = levelNum;
	}

	public Integer getUnifiedNum() {
		return unifiedNum;
	}

	public void setUnifiedNum(Integer unifiedNum) {
		this.unifiedNum = unifiedNum;
	}

}
