package com.irengine.campus.web.rest.dto;

import java.util.ArrayList;
import java.util.List;

import com.irengine.campus.domain.Teacher;

public class TeacherAndGroups {

	private Teacher teacher;
	
	//GroupDTO: course,isSelected,num
	private List<GroupDTO> groupDTOs = new ArrayList<GroupDTO>();

	public TeacherAndGroups() {
		super();
	}

	public TeacherAndGroups(Teacher teacher, List<GroupDTO> groupDTOs) {
		super();
		this.teacher = teacher;
		this.groupDTOs = groupDTOs;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public List<GroupDTO> getGroupDTOs() {
		return groupDTOs;
	}

	public void setGroupDTOs(List<GroupDTO> groupDTOs) {
		this.groupDTOs = groupDTOs;
	}
	
	
}
