package com.irengine.campus.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ele_statistical")
public class StatisticalDataOfSelectCourse extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -4570431621121700126L;
	
	@ManyToOne
	@JoinColumn(name="arrange_course_id")
	private ArrangeCourse arrangeCourse;
	
	@ManyToMany
	@JoinTable(name = "ele_statistical_statistical_assist", joinColumns = @JoinColumn(name = "statistical_id"), inverseJoinColumns = @JoinColumn(name = "assist_id"))
	private List<StatisticalDataOfSelectCourseAssist> data =new ArrayList<StatisticalDataOfSelectCourseAssist>();

	public StatisticalDataOfSelectCourse() {
		super();
	}

	public StatisticalDataOfSelectCourse(ArrangeCourse arrangeCourse, List<StatisticalDataOfSelectCourseAssist> data) {
		super();
		this.arrangeCourse = arrangeCourse;
		this.data = data;
	}

	public ArrangeCourse getArrangeCourse() {
		return arrangeCourse;
	}

	public void setArrangeCourse(ArrangeCourse arrangeCourse) {
		this.arrangeCourse = arrangeCourse;
	}

	public List<StatisticalDataOfSelectCourseAssist> getData() {
		return data;
	}

	public void setData(List<StatisticalDataOfSelectCourseAssist> data) {
		this.data = data;
	}
	
}
