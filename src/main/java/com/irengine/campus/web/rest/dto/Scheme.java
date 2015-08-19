package com.irengine.campus.web.rest.dto;

import java.util.ArrayList;
import java.util.List;

/*排课方案*/
public class Scheme {

	private Integer score;
	
	private List<GroupDTO3> groupDTO3s= new ArrayList<GroupDTO3>();

	public Scheme() {
		super();
	}

	public Scheme(Integer score, List<GroupDTO3> groupDTO3s) {
		super();
		this.score = score;
		this.groupDTO3s = groupDTO3s;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public List<GroupDTO3> getGroupDTO3s() {
		return groupDTO3s;
	}

	public void setGroupDTO3(List<GroupDTO3> groupDTO3s) {
		this.groupDTO3s = groupDTO3s;
	}
	
	
}
