package com.irengine.campus.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ele_teacher")
public class Teacher extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -6503625341869973509L;

	@OneToOne
	@JoinColumn(name = "course_id")
	// 该教师的课程
	private Course course;

	// 该老师带第几届学生
	private Integer th;

	// 该老师分组时默认带的班级
	@ManyToMany
	@JoinTable(name = "ele_teacher_natural_class", joinColumns = @JoinColumn(name = "teacher_id") , inverseJoinColumns = @JoinColumn(name = "natural_class_id") )
	private List<NaturalClass> nClasses = new ArrayList<NaturalClass>();

	@OneToOne
	@JoinColumn(name = "user_id")
	private UserBaseInfo baseInfo;

	public UserBaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(UserBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Integer getTh() {
		return th;
	}

	public void setTh(Integer th) {
		this.th = th;
	}

	public List<NaturalClass> getnClasses() {
		return nClasses;
	}

	public void setnClasses(List<NaturalClass> nClasses) {
		this.nClasses = nClasses;
	}

}
