package com.irengine.campus.web.rest.dto;

import java.util.Arrays;
import java.util.List;

public class CourseDTO implements Comparable<CourseDTO>{

	private String courseIds;
	
	private String courseNames;

	public CourseDTO() {
		super();
	}

	public CourseDTO(String courseIds, String courseNames) {
		super();
		this.courseIds = courseIds;
		this.courseNames = courseNames;
	}

	public String getCourseIds() {
		return courseIds;
	}

	public void setCourseIds(String courseIds) {
		this.courseIds = courseIds;
	}

	public String getCourseNames() {
		return courseNames;
	}

	public void setCourseNames(String courseNames) {
		this.courseNames = courseNames;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((courseIds == null) ? 0 : courseIds.hashCode());
		result = prime * result
				+ ((courseNames == null) ? 0 : courseNames.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseDTO other = (CourseDTO) obj;
		if (courseIds == null) {
			if (other.courseIds != null)
				return false;
		} else if (!courseIds.equals(other.courseIds))
			return false;
		if (courseNames == null) {
			if (other.courseNames != null)
				return false;
		} else if (!courseNames.equals(other.courseNames))
			return false;
		return true;
	}

	@Override
	public int compareTo(CourseDTO course) {
		// TODO Auto-generated method stub
    	String[] strs1=this.courseIds.split(",");
    	String[] strs2=course.getCourseIds().split(",");
    	List<String> ids1=Arrays.asList(strs1);
    	List<String> ids2=Arrays.asList(strs2);
    	if(ids1.size()!=ids2.size()){
    		return ids2.size()-ids1.size();
    	}else{
    		for(int i=0;i<ids1.size();i++){
    			if(Long.parseLong(ids1.get(i))!=Long.parseLong(ids2.get(i))){
    				return (int) (Long.parseLong(ids1.get(i))-Long.parseLong(ids2.get(i)));
    			}
    		}
    		return 0;
    	}
	}
    
}
