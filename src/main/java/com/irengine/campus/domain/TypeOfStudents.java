package com.irengine.campus.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 学生类别
 * 
 * @author wujing
 */
@Entity
@Table(name = "ele_student_type")
public class TypeOfStudents extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -4154623605033976023L;

	@Column(nullable = false, length = 50)
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
