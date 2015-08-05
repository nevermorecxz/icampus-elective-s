package com.irengine.campus.web.rest.dto;

import java.util.ArrayList;
import java.util.List;

public class ResultsDTOByCourses {

	private String title;
	
	private List<DataOfResultsDTOByCourses> data=new ArrayList<DataOfResultsDTOByCourses>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<DataOfResultsDTOByCourses> getData() {
		return data;
	}

	public void setData(List<DataOfResultsDTOByCourses> data) {
		this.data = data;
	}
	
}
