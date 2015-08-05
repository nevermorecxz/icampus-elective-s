package com.irengine.campus.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 自然班
 * 
 * @author huang
 *
 */
@Entity
@Table(name = "ele_nclass")
public class NaturalClass extends BaseEntity implements Serializable,Comparable<NaturalClass> {

	private static final long serialVersionUID = 3213914307361833259L;

	@Column(nullable = false, length = 50)
	private Long className;

	@Column(nullable = false)
	private Integer attendance;// 入学年份

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "type_id")
	private TypeOfNClass classType;

	@ManyToOne
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;

	public String getReturnClassType() {
		return classType.getTypeName();
	}

	/**
	 * 按照"高一(1)班"的格式返回
	 */
	public String getClassInfo() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		/* 7月份为升级节点 */
		int num=1;
		num=month>=8?year-attendance+1:year-attendance;
		if(num<=1){
			return attendance+"届高一("+className+")班";
		}else if(num==2){
			return attendance+"届高二("+className+")班";
		}else if(num==3){
			return attendance+"届高三("+className+")班";
		}else{
			return attendance+"届("+className+")班";
		}
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Integer getAttendance() {
		return attendance;
	}

	public void setAttendance(Integer attendance) {
		this.attendance = attendance;
	}

	public Long getClassName() {
		return className;
	}

	public void setClassName(Long className) {
		this.className = className;
	}

	public TypeOfNClass getClassType() {
		return classType;
	}

	public void setClassType(TypeOfNClass classType) {
		this.classType = classType;
	}

	@Override
	public int compareTo(NaturalClass o) {
		return (int) (this.className-o.getClassName());
	}

}
