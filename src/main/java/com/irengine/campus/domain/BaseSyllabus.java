package com.irengine.campus.domain;

import java.util.ArrayList;
import java.util.List;

public class BaseSyllabus {

	private List<ClassHourModule> classHourModules = new ArrayList<ClassHourModule>();

	public List<ClassHourModule> getClassHourModules() {
		return classHourModules;
	}

	public void setClassHourModules(List<ClassHourModule> classHourModules) {
		this.classHourModules = classHourModules;
	}
	
	
}
