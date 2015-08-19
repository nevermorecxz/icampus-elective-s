package com.irengine.campus.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 基本设置
 * 
 * @author wujing
 * */

@Entity
@Table(name = "ele_basic_setting")
public class BasicSettings extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -1563484011786865494L;

	private Integer th;
	
	//可选科目数
	private Integer numOfSubjects;
	
	//每个班可选该课程人数（0表示不限制）
	private Integer numOfClass;

	public BasicSettings() {
		super();
	}

	public BasicSettings(Integer th, Integer numOfSubjects, Integer numOfClass) {
		super();
		this.th = th;
		this.numOfSubjects = numOfSubjects;
		this.numOfClass = numOfClass;
	}

	public Integer getTh() {
		return th;
	}

	public void setTh(Integer th) {
		this.th = th;
	}

	public Integer getNumOfSubjects() {
		return numOfSubjects;
	}

	public void setNumOfSubjects(Integer numOfSubjects) {
		this.numOfSubjects = numOfSubjects;
	}

	public Integer getNumOfClass() {
		return numOfClass;
	}

	public void setNumOfClass(Integer numOfClass) {
		this.numOfClass = numOfClass;
	}
	
	
}
