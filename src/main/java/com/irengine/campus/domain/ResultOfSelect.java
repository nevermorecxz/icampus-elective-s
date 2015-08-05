package com.irengine.campus.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ele_select_result")
public class ResultOfSelect extends BaseEntity{

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="preferences_id")
	private Preferences preferences;
	
	@ManyToOne
	@JoinColumn(name="student_id")
	private Student student;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "ele_select_results_courses", joinColumns = @JoinColumn(name = "preferences_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
	private List<Course> courses=new ArrayList<Course>();
	
	public ResultOfSelect() {
		super();
	}

	public ResultOfSelect(Preferences preferences, Student student,
			List<Course> courses) {
		super();
		this.preferences = preferences;
		this.student = student;
		this.courses = courses;
	}

	public Long getPreferencesId(){
		return preferences.getId();
	}
	
	public String getCoursesInfo(){
		String str="";
		if(courses.size()>0){
			for(Course course:courses){
				str+=course.getCourseName()+",";
			}
			str=str.substring(0, str.length()-1);
		}
		return str;
	}
	
	public String getCourseIds(){
		String str="";
		if(courses.size()>0){
			for(Course course:courses){
				str+=course.getId()+",";
			}
			str=str.substring(0, str.length()-1);
		}
		return str;
	}
	
	public Preferences getPreferences() {
		return preferences;
	}

	public void setPreferences(Preferences preferences) {
		this.preferences = preferences;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	
}
