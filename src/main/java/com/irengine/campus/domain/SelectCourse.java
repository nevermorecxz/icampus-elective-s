package com.irengine.campus.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 关联学生,代表学生选课的结果
 * 
 * @author wujing
 */
@Entity
@Table(name = "ele_select_courses")
public class SelectCourse extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 2915575051132158334L;

	// 课程
	@OneToOne
	@JoinColumn(name = "course_id")
	private Course course;

	// 是否被选为等级考
	private boolean isSelected;

	// 记录(用于根据系统时间判断该课程已学多少个学期)
	private Integer year;

	// 0为上学期选择该门课,1为下学期选择该门课
	private Integer term;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "preferences_id")
	private Preferences preferences;

	public SelectCourse() {
		super();
	}

	public SelectCourse(Course course, boolean isSelected, Integer year, Integer term, Preferences preferences) {
		super();
		this.course = course;
		this.isSelected = isSelected;
		this.year = year;
		this.term = term;
		this.preferences = preferences;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	//----


	public boolean isSelected() {
		return isSelected;
	}

	public Integer getTerm() {
		return term;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Preferences getPreferences() {
		return preferences;
	}

	public void setPreferences(Preferences preferences) {
		this.preferences = preferences;
	}

}
