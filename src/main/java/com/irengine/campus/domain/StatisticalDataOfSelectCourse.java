package com.irengine.campus.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ele_statistical")
public class StatisticalDataOfSelectCourse extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -4570431621121700126L;
	
	@ManyToOne
	@JoinColumn(name="prefereneces_id")
	private Preferences preferences;
	
	@ManyToMany
	@JoinTable(name = "ele_statistical_statistical_assist", joinColumns = @JoinColumn(name = "statistical_id"), inverseJoinColumns = @JoinColumn(name = "assist_id"))
	private List<StatisticalDataOfSelectCourseAssist> data =new ArrayList<StatisticalDataOfSelectCourseAssist>();

	public Preferences getPreferences() {
		return preferences;
	}

	public void setPreferences(Preferences preferences) {
		this.preferences = preferences;
	}

	public List<StatisticalDataOfSelectCourseAssist> getData() {
		return data;
	}

	public void setData(List<StatisticalDataOfSelectCourseAssist> data) {
		this.data = data;
	}
	
}
