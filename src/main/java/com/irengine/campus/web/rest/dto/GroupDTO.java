package com.irengine.campus.web.rest.dto;

import com.irengine.campus.domain.Course;

public class GroupDTO {
	
	private Course course;
	
	private boolean isSelected;
	
	//group总数
	private Integer num;

	public GroupDTO() {
		super();
	}

	public GroupDTO(Course course, boolean isSelected, Integer num) {
		super();
		this.course = course;
		this.isSelected = isSelected;
		this.num = num;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	

}
