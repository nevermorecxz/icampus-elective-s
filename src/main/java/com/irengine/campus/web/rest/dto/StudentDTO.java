package com.irengine.campus.web.rest.dto;

import java.util.Comparator;

public class StudentDTO implements Comparable<StudentDTO>{

	private String classInfo;
	
	private String studentNum;
	
	private String name;
	
	private String selectInfo;

	public StudentDTO() {
		super();
	}

	public StudentDTO(String classInfo, String studentNum, String name,
			String selectInfo) {
		super();
		this.classInfo = classInfo;
		this.studentNum = studentNum;
		this.name = name;
		this.selectInfo = selectInfo;
	}

	public String getClassInfo() {
		return classInfo;
	}

	public void setClassInfo(String classInfo) {
		this.classInfo = classInfo;
	}

	public String getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSelectInfo() {
		return selectInfo;
	}

	public void setSelectInfo(String selectInfo) {
		this.selectInfo = selectInfo;
	}

	@Override
	public int compareTo(StudentDTO o2) {
		String studentNum1=this.studentNum;
		String studentNum2=o2.getStudentNum();
		int num1=0;
		int num2=0;
		if(studentNum1.length()>8&&studentNum2.length()>8){
			num1=Integer.parseInt(studentNum1.substring(studentNum1.length()-8, studentNum1.length()));
			num2=Integer.parseInt(studentNum2.substring(studentNum2.length()-8, studentNum2.length()));
		}
		return num1-num2;
	}
	
}
