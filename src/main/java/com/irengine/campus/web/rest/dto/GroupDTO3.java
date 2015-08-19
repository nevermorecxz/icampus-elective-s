package com.irengine.campus.web.rest.dto;

import com.irengine.campus.domain.Group;
/*group划分模块数据*/
public class GroupDTO3 {
	
	int classHourModuleIndex;
	
	private int scores;
	
	private Group group;

	public GroupDTO3() {
		super();
	}

	public GroupDTO3(int classHourModuleIndex, int scores, Group group) {
		super();
		this.classHourModuleIndex = classHourModuleIndex;
		this.scores = scores;
		this.group = group;
	}

	public int getClassHourModuleIndex() {
		return classHourModuleIndex;
	}

	public void setClassHourModuleIndex(int classHourModuleIndex) {
		this.classHourModuleIndex = classHourModuleIndex;
	}

	public int getScores() {
		return scores;
	}

	public void setScores(int scores) {
		this.scores = scores;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	
	
}
