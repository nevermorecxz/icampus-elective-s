package com.irengine.campus.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 学生
 * 
 * @author wujing
 *
 */
@Entity
@Table(name = "ele_students")
public class Student extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -4667021606365108955L;

	@Column(nullable = false, unique = true, length = 50)
	private String studentNum;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "type_id")
	private TypeOfStudents type;// 学生类型,比如:正式生,借读生...

	@ManyToOne
	@JoinColumn(name = "n_class_id")
	private NaturalClass nClass;// 自然班级

	@OneToOne
	@JoinColumn(name = "user_id")
	private UserBaseInfo baseInfo;

	//学生的已选科目记录(记录是否为等级课,选择时间)
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "ele_students_courses", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private List<SelectCourse> selectCourses = new ArrayList<SelectCourse>();

	/** 返回学生类型 */
	public String getReturnType() {
		return type.getType();
	}

	public String getStudentNum() {
		return studentNum;
	}

	public String getCourseIds() {
		String str="";
		if(selectCourses.size()>0){
			for(SelectCourse selectCourse:selectCourses){
				if(selectCourse.isSelected()){
					str+=selectCourse.getCourse().getId()+",";
				}
			}
			str=str.substring(0, str.length()-1);
		}
		return str;
	}
	
	public String getCoursesInfo(){
		String str="";
		if(selectCourses.size()>0){
			for(SelectCourse selectCourse:selectCourses){
				if(selectCourse.isSelected()){
					str+=selectCourse.getCourse().getCourseName()+",";
				}
			}
			str=str.substring(0, str.length()-1);
		}
		return str;
	}
	
	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}

	public TypeOfStudents getType() {
		return type;
	}

	public void setType(TypeOfStudents type) {
		this.type = type;
	}

	public NaturalClass getnClass() {
		return nClass;
	}

	public void setnClass(NaturalClass nClass) {
		this.nClass = nClass;
	}

	public UserBaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(UserBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public List<SelectCourse> getSelectCourses() {
		return selectCourses;
	}

	public void setSelectCourses(List<SelectCourse> selectCourses) {
		this.selectCourses = selectCourses;
	}

}
