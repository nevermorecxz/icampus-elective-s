package com.irengine.campus.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 选课设置参数
 * 
 * @author huang
 *
 */
@Entity
@Table(name = "ele_preferences")
public class Preferences extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -2550601916320007445L;

	// 第几届选课
	private Integer th;

	@Column(length = 500)
	private String info;

	@Column(nullable = false, length = 50)
	@Pattern(regexp = "[>||=||<][,][0-9]*$")
	private String num;// 格式:<,1

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date createdTime;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date startDate;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date endDate;

	@ManyToMany
	@JoinTable(name = "ele_preferences_courses", joinColumns = @JoinColumn(name = "preferences_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private List<Course> courses = new ArrayList<Course>();

	@ManyToMany
	@JoinTable(name = "ele_preferences_students", joinColumns = @JoinColumn(name = "preferences_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
	private List<Student> students = new ArrayList<Student>();

	@ManyToMany
	@JoinTable(name = "ele_preferences_classes", joinColumns = @JoinColumn(name = "preferences_id"), inverseJoinColumns = @JoinColumn(name = "class_id"))
	private List<NaturalClass> classes = new ArrayList<NaturalClass>();

	public Preferences() {
		super();
	}

	public Preferences(String name, Date createdTime, Date startDate,
			Date endDate, List<Course> courses, String num, String info,
			Integer th) {
		super();
		this.name = name;
		this.createdTime = createdTime;
		this.startDate = startDate;
		this.endDate = endDate;
		this.courses = courses;
		this.num = num;
		this.info = info;
		this.th = th;
	}

	public Integer getTh() {
		return th;
	}

	public void setTh(Integer th) {
		this.th = th;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<NaturalClass> getClasses() {
		return classes;
	}

	public void setClasses(List<NaturalClass> classes) {
		this.classes = classes;
	}

}
