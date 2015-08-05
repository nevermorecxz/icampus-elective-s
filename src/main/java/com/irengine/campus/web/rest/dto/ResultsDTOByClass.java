package com.irengine.campus.web.rest.dto;

import java.util.ArrayList;
import java.util.List;

public class ResultsDTOByClass {
	
	private String title;
	
	private String teacherInfo;
	
	private List<StudentDTO> students=new ArrayList<StudentDTO>();

	public ResultsDTOByClass() {
		super();
	}

	public ResultsDTOByClass(String title, String teacherInfo,
			List<StudentDTO> students) {
		super();
		this.title = title;
		this.teacherInfo = teacherInfo;
		this.students = students;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTeacherInfo() {
		return teacherInfo;
	}

	public void setTeacherInfo(String teacherInfo) {
		this.teacherInfo = teacherInfo;
	}

	public List<StudentDTO> getStudents() {
		return students;
	}

	public void setStudents(List<StudentDTO> students) {
		this.students = students;
	}
	
}
