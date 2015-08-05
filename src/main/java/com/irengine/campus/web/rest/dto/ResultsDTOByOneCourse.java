package com.irengine.campus.web.rest.dto;

import java.util.ArrayList;
import java.util.List;

public class ResultsDTOByOneCourse {

	private String title;
	
	private String courseName;
	
	private List<StudentDTO> students=new ArrayList<StudentDTO>();

	public ResultsDTOByOneCourse() {
		super();
	}

	public ResultsDTOByOneCourse(String title, String courseName,
			List<StudentDTO> students) {
		super();
		this.title = title;
		this.courseName = courseName;
		this.students = students;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public List<StudentDTO> getStudents() {
		return students;
	}

	public void setStudents(List<StudentDTO> students) {
		this.students = students;
	}
	
}
