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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 分组
 * 
 * @author wujing
 */

@Entity
@Table(name = "ele_table")
public class Group extends BaseEntity implements Serializable, Comparable<Group> {

	private static final long serialVersionUID = -3151562699728522116L;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "ele_group_student", joinColumns = @JoinColumn(name = "group_id") , inverseJoinColumns = @JoinColumn(name = "student_id") )
	private List<Student> students = new ArrayList<Student>();

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	@JsonIgnore
	private boolean isSelected;

	@JsonIgnore
	private ArrangeCourse arrangeCourse;

	public Group() {
		super();
	}

	public Group(List<Student> students, Teacher teacher, Course course, boolean isSelected,
			ArrangeCourse arrangeCourse) {
		super();
		this.students = students;
		this.teacher = teacher;
		this.course = course;
		this.isSelected = isSelected;
		this.arrangeCourse = arrangeCourse;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public ArrangeCourse getArrangeCourse() {
		return arrangeCourse;
	}

	public void setArrangeCourse(ArrangeCourse arrangeCourse) {
		this.arrangeCourse = arrangeCourse;
	}

	@Override
	public int compareTo(Group o) {
		return o.getStudents().size() - students.size();
	}
	
	public String getInfo(){
		return teacher.getCourse().getCourseName()+"老师:"+teacher.getBaseInfo().getName()+","+(isSelected()?"等级考":"会考");
	}
	
//	public Integer getStudentSize() {
//		return students.size();
//	}
}
